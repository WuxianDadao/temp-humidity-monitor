package com.iot.temphumidity.service.postgresql;

import com.iot.temphumidity.dto.alarm.AlarmHistoryDTO;
import com.iot.temphumidity.entity.postgresql.AlarmHistoryEntity;
import com.iot.temphumidity.entity.postgresql.AlarmConfigEntity;
import com.iot.temphumidity.entity.postgresql.SensorTagEntity;
import com.iot.temphumidity.entity.postgresql.GatewayEntity;
import com.iot.temphumidity.entity.postgresql.DeviceEntity;
import com.iot.temphumidity.repository.postgresql.AlarmHistoryRepository;
import com.iot.temphumidity.repository.postgresql.AlarmConfigRepository;
import com.iot.temphumidity.repository.postgresql.SensorTagRepository;
import com.iot.temphumidity.repository.postgresql.GatewayRepository;
import com.iot.temphumidity.repository.postgresql.DeviceRepository;
import com.iot.temphumidity.mapper.AlarmHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 报警历史服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(transactionManager = "postgresqlTransactionManager")
public class AlarmHistoryService {
    
    private final AlarmHistoryRepository alarmHistoryRepository;
    private final AlarmConfigRepository alarmConfigRepository;
    private final SensorTagRepository sensorTagRepository;
    private final GatewayRepository gatewayRepository;
    private final DeviceRepository deviceRepository;
    private final AlarmHistoryMapper alarmHistoryMapper;
    
    /**
     * 创建报警记录
     */
    public AlarmHistoryDTO createAlarmHistory(AlarmHistoryDTO dto) {
        AlarmHistoryEntity entity = alarmHistoryMapper.toEntity(dto);
        entity.setTriggerTime(LocalDateTime.now());
        entity.setStatus("TRIGGERED");
        
        // 关联传感器
        if (dto.getSensorId() != null) {
            // 首先尝试通过tagId查找传感器
            Optional<SensorTagEntity> sensorOpt = sensorTagRepository.findByTagId(dto.getSensorId());
            if (sensorOpt.isEmpty()) {
                // 如果tagId查找失败，尝试通过ID查找（需要解析）
                try {
                    Long sensorId = Long.parseLong(dto.getSensorId());
                    sensorOpt = sensorTagRepository.findById(sensorId);
                } catch (NumberFormatException e) {
                    // 如果sensorId不是数字，抛出异常
                    throw new IllegalArgumentException("传感器不存在: " + dto.getSensorId());
                }
            }
            
            SensorTagEntity sensor = sensorOpt.orElseThrow(() -> 
                new IllegalArgumentException("传感器不存在: " + dto.getSensorId()));
            
            entity.setSensorId(sensor.getTagId());
            // 需要将String类型的deviceId和gatewayId转换为Long
            if (sensor.getDeviceId() != null) {
                try {
                    entity.setDeviceId(Long.parseLong(sensor.getDeviceId()));
                } catch (NumberFormatException e) {
                    // 如果deviceId不是数字，设置为null
                    entity.setDeviceId(null);
                }
            }
            if (sensor.getGatewayIdLong() != null) {
                try {
                    entity.setGatewayId(sensor.getGatewayIdLong());
                } catch (NumberFormatException e) {
                    // 如果gatewayId不是数字，设置为null
                    entity.setGatewayId(null);
                }
            }
            
            // 设置关联名称
            if (sensor.getGatewayIdLong() != null) {
                try {
                    Long gatewayId = sensor.getGatewayIdLong();
                    gatewayRepository.findById(gatewayId)
                            .ifPresent(gateway -> entity.setGatewayName(gateway.getName()));
                } catch (NumberFormatException e) {
                    // 如果gatewayId不是数字，跳过
                }
            }
            if (sensor.getDeviceId() != null) {
                try {
                    Long deviceId = Long.parseLong(sensor.getDeviceId());
                    deviceRepository.findById(deviceId)
                            .ifPresent(device -> entity.setDeviceName(device.getName()));
                } catch (NumberFormatException e) {
                    // 如果deviceId不是数字，跳过
                }
            }
        }
        
        // 关联报警配置
        if (dto.getAlarmConfigId() != null) {
            AlarmConfigEntity config = alarmConfigRepository.findById(dto.getAlarmConfigId())
                    .orElseThrow(() -> new IllegalArgumentException("报警配置不存在: " + dto.getAlarmConfigId()));
            entity.setAlarmConfigId(config.getId());
            entity.setAlarmType(config.getAlarmType());
            entity.setSeverity(config.getSeverity());
            entity.setThresholdMin(config.getThresholdMin());
            entity.setThresholdMax(config.getThresholdMax());
            
            // 设置自定义字段
            if (config.getCustomFields() != null) {
                entity.setCustomFields(config.getCustomFields());
            }
        }
        
        AlarmHistoryEntity saved = alarmHistoryRepository.save(entity);
        return alarmHistoryMapper.toDTO(saved);
    }
    
    /**
     * 触发报警
     */
    public AlarmHistoryDTO triggerAlarm(String sensorId, String alarmType, String message, 
                                       Double triggerValue, Double thresholdMin, Double thresholdMax) {
        AlarmHistoryDTO dto = new AlarmHistoryDTO();
        dto.setSensorId(sensorId);
        dto.setAlarmType(alarmType);
        dto.setMessage(message != null ? message : generateAlarmMessage(alarmType, triggerValue));
        dto.setTriggerValue(triggerValue);
        dto.setThresholdMin(thresholdMin);
        dto.setThresholdMax(thresholdMax);
        dto.setSeverity(determineSeverity(alarmType, triggerValue, thresholdMin, thresholdMax));
        
        return createAlarmHistory(dto);
    }
    
    /**
     * 确认报警
     */
    public AlarmHistoryDTO acknowledgeAlarm(Long id, Long userId, String comment) {
        AlarmHistoryEntity entity = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("报警记录不存在: " + id));
        
        if ("RESOLVED".equals(entity.getStatus())) {
            throw new IllegalStateException("报警已解决，无法确认");
        }
        
        if (entity.getAcknowledgedTime() != null) {
            throw new IllegalStateException("报警已确认");
        }
        
        entity.setStatus("ACKNOWLEDGED");
        entity.setAcknowledgedTime(LocalDateTime.now());
        entity.setAcknowledgedBy(userId);
        entity.setAcknowledgedComment(comment);
        
        AlarmHistoryEntity saved = alarmHistoryRepository.save(entity);
        return alarmHistoryMapper.toDTO(saved);
    }
    
    /**
     * 解决报警
     */
    public AlarmHistoryDTO resolveAlarm(Long id, Long userId, String resolution) {
        AlarmHistoryEntity entity = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("报警记录不存在: " + id));
        
        if ("RESOLVED".equals(entity.getStatus())) {
            throw new IllegalStateException("报警已解决");
        }
        
        entity.setStatus("RESOLVED");
        entity.setResolveTime(LocalDateTime.now());
        entity.setResolvedBy(userId);
        entity.setResolution(resolution);
        
        // 计算持续时间
        if (entity.getTriggerTime() != null) {
            Duration duration = Duration.between(entity.getTriggerTime(), entity.getResolveTime());
            entity.setCalculatedDuration(duration.toSeconds());
        }
        
        AlarmHistoryEntity saved = alarmHistoryRepository.save(entity);
        return alarmHistoryMapper.toDTO(saved);
    }
    
    /**
     * 自动解决报警
     */
    public AlarmHistoryDTO autoResolveAlarm(Long id, String resolution) {
        AlarmHistoryEntity entity = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("报警记录不存在: " + id));
        
        if ("RESOLVED".equals(entity.getStatus())) {
            return alarmHistoryMapper.toDTO(entity);
        }
        
        entity.setStatus("RESOLVED");
        entity.setResolveTime(LocalDateTime.now());
        entity.setResolution(resolution != null ? resolution : "系统自动解决");
        
        // 计算持续时间
        if (entity.getTriggerTime() != null) {
            Duration duration = Duration.between(entity.getTriggerTime(), entity.getResolveTime());
            entity.setCalculatedDuration(duration.toSeconds());
        }
        
        AlarmHistoryEntity saved = alarmHistoryRepository.save(entity);
        return alarmHistoryMapper.toDTO(saved);
    }
    
    /**
     * 获取报警记录
     */
    public AlarmHistoryDTO getAlarmHistory(Long id) {
        AlarmHistoryEntity entity = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("报警记录不存在: " + id));
        
        AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
        enrichDTOWithRelatedInfo(dto, entity);
        return dto;
    }
    
    /**
     * 分页查询报警记录
     */
    public Page<AlarmHistoryDTO> getAlarmHistories(Pageable pageable) {
        Page<AlarmHistoryEntity> page = alarmHistoryRepository.findAll(pageable);
        return page.map(entity -> {
            AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
            enrichDTOWithRelatedInfo(dto, entity);
            return dto;
        });
    }
    
    /**
     * 搜索报警记录
     */
    public Page<AlarmHistoryDTO> searchAlarmHistories(String sensorId, Long gatewayId, Long deviceId,
                                                     String alarmType, String severity, Boolean resolved,
                                                     Boolean acknowledged, LocalDateTime startTime,
                                                     LocalDateTime endTime, Pageable pageable) {
        Page<AlarmHistoryEntity> page = alarmHistoryRepository.searchAlarmHistories(
                sensorId, gatewayId, deviceId, alarmType, severity, 
                resolved, acknowledged, startTime, endTime, pageable);
        
        return page.map(entity -> {
            AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
            enrichDTOWithRelatedInfo(dto, entity);
            return dto;
        });
    }
    
    /**
     * 获取活动报警
     */
    public List<AlarmHistoryDTO> getActiveAlarms() {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findActiveAlarms();
        return entities.stream()
                .map(entity -> {
                    AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
                    enrichDTOWithRelatedInfo(dto, entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 获取未确认报警
     */
    public List<AlarmHistoryDTO> getUnacknowledgedAlarms() {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findUnacknowledgedAlarms();
        return entities.stream()
                .map(entity -> {
                    AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
                    enrichDTOWithRelatedInfo(dto, entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 获取传感器活动报警
     */
    public List<AlarmHistoryDTO> getActiveAlarmsBySensorId(String sensorId) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findActiveAlarmsBySensorId(sensorId);
        return entities.stream()
                .map(entity -> {
                    AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
                    enrichDTOWithRelatedInfo(dto, entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 获取网关活动报警
     */
    public List<AlarmHistoryDTO> getActiveAlarmsByGatewayId(Long gatewayId) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findActiveAlarmsByGatewayId(gatewayId);
        return entities.stream()
                .map(entity -> {
                    AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
                    enrichDTOWithRelatedInfo(dto, entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 获取时间范围内报警
     */
    public List<AlarmHistoryDTO> getAlarmsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findAlarmsByTimeRange(startTime, endTime);
        return entities.stream()
                .map(entity -> {
                    AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
                    enrichDTOWithRelatedInfo(dto, entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 获取配置相关报警
     */
    public List<AlarmHistoryDTO> getAlarmsByConfigId(Long configId) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findAlarmsByConfigId(configId);
        return entities.stream()
                .map(entity -> {
                    AlarmHistoryDTO dto = alarmHistoryMapper.toDTO(entity);
                    enrichDTOWithRelatedInfo(dto, entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 获取活动报警数量
     */
    public long countActiveAlarms() {
        return alarmHistoryRepository.countActiveAlarms();
    }
    
    /**
     * 获取未确认报警数量
     */
    public long countUnacknowledgedAlarms() {
        return alarmHistoryRepository.countUnacknowledgedAlarms();
    }
    
    /**
     * 获取报警类型统计
     */
    public List<Object[]> getAlarmTypeStatistics() {
        return alarmHistoryRepository.getAlarmTypeStatistics();
    }
    
    /**
     * 获取严重程度统计
     */
    public List<Object[]> getSeverityStatistics() {
        return alarmHistoryRepository.getSeverityStatistics();
    }
    
    /**
     * 获取每日报警统计
     */
    public List<Object[]> getAlarmsByDate(LocalDateTime startDate) {
        return alarmHistoryRepository.getAlarmsByDate(startDate);
    }
    
    /**
     * 获取每小时报警统计
     */
    public List<Object[]> getAlarmsByHour(LocalDateTime startTime) {
        return alarmHistoryRepository.getAlarmsByHour(startTime);
    }
    
    /**
     * 获取平均解决时间
     */
    public Double getAverageResolveTime() {
        return alarmHistoryRepository.getAverageResolveTime();
    }
    
    /**
     * 批量确认报警
     */
    public List<AlarmHistoryDTO> batchAcknowledgeAlarms(List<Long> ids, Long userId, String comment) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findAllById(ids);
        if (entities.isEmpty()) {
            throw new IllegalArgumentException("未找到指定的报警记录");
        }
        
        return entities.stream()
                .filter(entity -> !"RESOLVED".equals(entity.getStatus()) && entity.getAcknowledgedTime() == null)
                .map(entity -> {
                    entity.setStatus("ACKNOWLEDGED");
                    entity.setAcknowledgedTime(LocalDateTime.now());
                    entity.setAcknowledgedBy(userId);
                    entity.setAcknowledgedComment(comment);
                    alarmHistoryRepository.save(entity);
                    return alarmHistoryMapper.toDTO(entity);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 批量解决报警
     */
    public List<AlarmHistoryDTO> batchResolveAlarms(List<Long> ids, Long userId, String resolution) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findAllById(ids);
        if (entities.isEmpty()) {
            throw new IllegalArgumentException("未找到指定的报警记录");
        }
        
        return entities.stream()
                .filter(entity -> !"RESOLVED".equals(entity.getStatus()))
                .map(entity -> {
                    entity.setStatus("RESOLVED");
                    entity.setResolveTime(LocalDateTime.now());
                    entity.setResolvedBy(userId);
                    entity.setResolution(resolution);
                    
                    // 计算持续时间
                    if (entity.getTriggerTime() != null) {
                        Duration duration = Duration.between(entity.getTriggerTime(), entity.getResolveTime());
                        entity.setCalculatedDuration(duration.toSeconds());
                    }
                    
                    alarmHistoryRepository.save(entity);
                    return alarmHistoryMapper.toDTO(entity);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 清理过期报警记录
     */
    public int cleanupExpiredAlarms(int daysToKeep) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(daysToKeep);
        List<AlarmHistoryEntity> expired = alarmHistoryRepository.findExpiredAlarms(cutoffTime);
        
        if (!expired.isEmpty()) {
            alarmHistoryRepository.deleteAll(expired);
            log.info("清理过期报警记录 {} 条", expired.size());
        }
        
        return expired.size();
    }
    
    /**
     * 更新报警记录
     */
    public AlarmHistoryDTO updateAlarmHistory(Long id, AlarmHistoryDTO dto) {
        AlarmHistoryEntity entity = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("报警记录不存在: " + id));
        
        alarmHistoryMapper.updateEntityFromDTO(dto, entity);
        AlarmHistoryEntity saved = alarmHistoryRepository.save(entity);
        
        AlarmHistoryDTO result = alarmHistoryMapper.toDTO(saved);
        enrichDTOWithRelatedInfo(result, saved);
        return result;
    }
    
    /**
     * 删除报警记录
     */
    public void deleteAlarmHistory(Long id) {
        AlarmHistoryEntity entity = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("报警记录不存在: " + id));
        
        alarmHistoryRepository.delete(entity);
        log.info("删除报警记录: {}", id);
    }
    
    /**
     * 批量删除报警记录
     */
    public void batchDeleteAlarmHistories(List<Long> ids) {
        List<AlarmHistoryEntity> entities = alarmHistoryRepository.findAllById(ids);
        if (!entities.isEmpty()) {
            alarmHistoryRepository.deleteAll(entities);
            log.info("批量删除报警记录 {} 条", entities.size());
        }
    }
    
    /**
     * 获取报警趋势数据
     */
    public List<Object[]> getAlarmTrendData(LocalDateTime startTime, LocalDateTime endTime, 
                                           String groupingType) {
        return alarmHistoryRepository.getAlarmTrendData(startTime, endTime, groupingType);
    }
    
    /**
     * 获取报警性能指标
     */
    public Object getAlarmPerformanceMetrics(LocalDateTime startTime, LocalDateTime endTime) {
        return alarmHistoryRepository.getAlarmPerformanceMetrics(startTime, endTime);
    }
    
    // 私有辅助方法
    private String generateAlarmMessage(String alarmType, Double triggerValue) {
        switch (alarmType) {
            case "TEMPERATURE_HIGH":
                return String.format("温度过高: %.1f°C", triggerValue);
            case "TEMPERATURE_LOW":
                return String.format("温度过低: %.1f°C", triggerValue);
            case "HUMIDITY_HIGH":
                return String.format("湿度过高: %.1f%%", triggerValue);
            case "HUMIDITY_LOW":
                return String.format("湿度过低: %.1f%%", triggerValue);
            case "BATTERY_LOW":
                return String.format("电量过低: %.1f%%", triggerValue);
            case "DEVICE_OFFLINE":
                return "设备离线";
            default:
                return String.format("报警: %s - %.1f", alarmType, triggerValue);
        }
    }
    
    private String determineSeverity(String alarmType, Double triggerValue, 
                                    Double thresholdMin, Double thresholdMax) {
        if (alarmType.contains("HIGH") || alarmType.contains("LOW")) {
            // 计算偏离程度
            if (thresholdMin != null && thresholdMax != null) {
                double range = thresholdMax - thresholdMin;
                double deviation = Math.abs(triggerValue - (thresholdMin + thresholdMax) / 2);
                double deviationPercent = (deviation / range) * 100;
                
                if (deviationPercent > 50) {
                    return "CRITICAL";
                } else if (deviationPercent > 30) {
                    return "HIGH";
                } else if (deviationPercent > 15) {
                    return "MEDIUM";
                } else {
                    return "LOW";
                }
            }
        }
        
        // 默认严重级别
        if (alarmType.contains("OFFLINE") || alarmType.contains("FAILURE")) {
            return "HIGH";
        } else if (alarmType.contains("WARNING")) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }
    
    /**
     * 清理旧的报警历史记录
     */
    public int cleanupOldAlarms(LocalDate retentionDate) {
        // 删除超过保留日期的报警历史记录
        LocalDateTime cutoffDateTime = retentionDate.atStartOfDay();
        List<AlarmHistoryEntity> oldAlarms = alarmHistoryRepository.findAll()
                .stream()
                .filter(alarm -> alarm.getTriggerTime() != null && alarm.getTriggerTime().isBefore(cutoffDateTime))
                .collect(Collectors.toList());
        
        if (!oldAlarms.isEmpty()) {
            alarmHistoryRepository.deleteAll(oldAlarms);
            return oldAlarms.size();
        }
        return 0;
    }
    
    /**
     * 丰富DTO相关信息
     */
    public void enrichDTOWithRelatedInfo(AlarmHistoryDTO dto, AlarmHistoryEntity entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        // 如果DTO已经有传感器信息，直接返回
        if (dto.getSensorName() != null && dto.getGatewayName() != null) {
            return;
        }
        
        // 获取传感器信息
        if (entity.getSensorId() != null) {
            try {
                // 首先尝试通过tagId查找传感器
                Optional<SensorTagEntity> sensorOpt = sensorTagRepository.findByTagId(entity.getSensorId());
                if (sensorOpt.isEmpty()) {
                    // 如果tagId查找失败，尝试通过ID查找（需要解析）
                    try {
                        Long sensorId = Long.parseLong(entity.getSensorId());
                        sensorOpt = sensorTagRepository.findById(sensorId);
                    } catch (NumberFormatException e) {
                        // 如果sensorId不是数字，跳过ID查找
                    }
                }
                
                if (sensorOpt.isPresent()) {
                    SensorTagEntity sensor = sensorOpt.get();
                    dto.setSensorName(sensor.getName());
                    dto.setGatewayId(sensor.getGatewayIdLong());
                    
                    // 获取网关信息
                    if (sensor.getGatewayIdLong() != null) {
                        try {
                            Long gatewayId = sensor.getGatewayIdLong();
                            gatewayRepository.findById(gatewayId).ifPresent(gateway -> {
                                dto.setGatewayName(gateway.getName());
                            });
                        } catch (NumberFormatException e) {
                            log.warn("网关ID不是有效的数字: {}", sensor.getGatewayId());
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("无法获取传感器信息: {}", e.getMessage());
            }
        }
    }
    
    /**
     * 丰富DTO相关信息（单参数版本）
     */
    public AlarmHistoryDTO enrichDTOWithRelatedInfo(AlarmHistoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        // 如果DTO已经有传感器信息，直接返回
        if (dto.getSensorName() != null && dto.getGatewayName() != null) {
            return dto;
        }
        
        // 获取传感器信息
        if (dto.getSensorId() != null) {
            try {
                // 尝试通过tagId查找
                Optional<SensorTagEntity> sensorOpt = sensorTagRepository.findByTagId(dto.getSensorId());
                if (sensorOpt.isEmpty()) {
                    // 尝试通过ID查找
                    try {
                        Long sensorId = Long.parseLong(dto.getSensorId());
                        sensorOpt = sensorTagRepository.findById(sensorId);
                    } catch (NumberFormatException e) {
                        // 如果sensorId不是数字，继续
                    }
                }
                
                if (sensorOpt.isPresent()) {
                    SensorTagEntity sensor = sensorOpt.get();
                    dto.setSensorName(sensor.getName());
                    dto.setGatewayId(sensor.getGatewayIdLong());
                    
                    // 获取网关信息
                    if (sensor.getGatewayIdLong() != null) {
                        try {
                            Long gatewayId = sensor.getGatewayIdLong();
                            gatewayRepository.findById(gatewayId).ifPresent(gateway -> {
                                dto.setGatewayName(gateway.getName());
                            });
                        } catch (NumberFormatException e) {
                            log.warn("网关ID不是有效的数字: {}", sensor.getGatewayId());
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("无法获取传感器信息: {}", e.getMessage());
            }
        }
        
        return dto;
    }
}