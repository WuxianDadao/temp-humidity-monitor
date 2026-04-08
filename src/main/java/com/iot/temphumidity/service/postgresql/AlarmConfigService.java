package com.iot.temphumidity.service.postgresql;

import com.iot.temphumidity.dto.alarm.AlarmConfigDTO;
import com.iot.temphumidity.entity.postgresql.AlarmConfigEntity;
import com.iot.temphumidity.repository.postgresql.AlarmConfigRepository;
import com.iot.temphumidity.mapper.AlarmConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 报警配置服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmConfigService {
    
    private final AlarmConfigRepository alarmConfigRepository;
    private final AlarmConfigMapper alarmConfigMapper;
    
    /**
     * 创建报警配置
     */
    @Transactional
    public AlarmConfigDTO createAlarmConfig(AlarmConfigDTO dto) {
        log.info("创建报警配置: {}", dto.getName());
        
        // 验证名称唯一性
        if (alarmConfigRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("报警配置名称已存在: " + dto.getName());
        }
        
        // 验证阈值
        if (!dto.validateThresholds()) {
            throw new IllegalArgumentException("阈值设置无效: 最小值不能大于最大值");
        }
        
        AlarmConfigEntity entity = alarmConfigMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(1);
        
        AlarmConfigEntity savedEntity = alarmConfigRepository.save(entity);
        log.info("报警配置创建成功, ID: {}", savedEntity.getId());
        
        return alarmConfigMapper.toDTO(savedEntity);
    }
    
    /**
     * 更新报警配置
     */
    @Transactional
    public AlarmConfigDTO updateAlarmConfig(Long id, AlarmConfigDTO dto) {
        log.info("更新报警配置, ID: {}", id);
        
        AlarmConfigEntity entity = alarmConfigRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("报警配置不存在: " + id));
        
        // 验证名称唯一性（如果修改了名称）
        if (!entity.getName().equals(dto.getName()) && alarmConfigRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("报警配置名称已存在: " + dto.getName());
        }
        
        // 验证阈值
        if (!dto.validateThresholds()) {
            throw new IllegalArgumentException("阈值设置无效: 最小值不能大于最大值");
        }
        
        alarmConfigMapper.updateEntityFromDTO(dto, entity);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        
        AlarmConfigEntity updatedEntity = alarmConfigRepository.save(entity);
        log.info("报警配置更新成功, ID: {}", id);
        
        return alarmConfigMapper.toDTO(updatedEntity);
    }
    
    /**
     * 获取报警配置
     */
    @Transactional(readOnly = true)
    public AlarmConfigDTO getAlarmConfig(Long id) {
        AlarmConfigEntity entity = alarmConfigRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("报警配置不存在: " + id));
        return alarmConfigMapper.toDTO(entity);
    }
    
    /**
     * 删除报警配置
     */
    @Transactional
    public void deleteAlarmConfig(Long id) {
        if (!alarmConfigRepository.existsById(id)) {
            throw new EntityNotFoundException("报警配置不存在: " + id);
        }
        alarmConfigRepository.deleteById(id);
        log.info("报警配置删除成功, ID: {}", id);
    }
    
    /**
     * 分页查询报警配置
     */
    @Transactional(readOnly = true)
    public Page<AlarmConfigDTO> getAlarmConfigs(Pageable pageable) {
        return alarmConfigRepository.findAll(pageable)
                .map(alarmConfigMapper::toDTO);
    }
    
    /**
     * 搜索报警配置
     */
    @Transactional(readOnly = true)
    public Page<AlarmConfigDTO> searchAlarmConfigs(String name, String alarmType, 
                                                  String severity, Boolean enabled, 
                                                  Pageable pageable) {
        return alarmConfigRepository.searchAlarms(name, alarmType, severity, enabled, pageable)
                .map(alarmConfigMapper::toDTO);
    }
    
    /**
     * 获取传感器相关的报警配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getAlarmConfigsBySensorId(String sensorId) {
        return alarmConfigRepository.findBySensorId(sensorId).stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取网关相关的报警配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getAlarmConfigsByGatewayId(Long gatewayId) {
        return alarmConfigRepository.findByGatewayId(gatewayId).stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取设备相关的报警配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getAlarmConfigsByDeviceId(Long deviceId) {
        return alarmConfigRepository.findByDeviceId(deviceId).stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取活动的报警配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getActiveAlarmConfigs() {
        return alarmConfigRepository.findByEnabled(true).stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取传感器适用的报警配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getApplicableAlarmConfigs(String sensorId, Long gatewayId, Long deviceId) {
        return alarmConfigRepository.findApplicableAlarms(sensorId, gatewayId, deviceId).stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 启用/禁用报警配置
     */
    @Transactional
    public AlarmConfigDTO toggleAlarmConfig(Long id, boolean enabled) {
        AlarmConfigEntity entity = alarmConfigRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("报警配置不存在: " + id));
        
        entity.setEnabled(enabled);
        entity.setUpdatedAt(LocalDateTime.now());
        
        AlarmConfigEntity updatedEntity = alarmConfigRepository.save(entity);
        log.info("报警配置 {} 状态更新为: {}", id, enabled ? "启用" : "禁用");
        
        return alarmConfigMapper.toDTO(updatedEntity);
    }
    
    /**
     * 获取报警类型统计
     */
    @Transactional(readOnly = true)
    public List<Object[]> getAlarmTypeStatistics() {
        return alarmConfigRepository.countActiveAlarmsByType();
    }
    
    /**
     * 获取严重程度统计
     */
    @Transactional(readOnly = true)
    public List<Object[]> getSeverityStatistics() {
        return alarmConfigRepository.countActiveAlarmsBySeverity();
    }
    
    /**
     * 获取活动报警配置数量
     */
    @Transactional(readOnly = true)
    public long countActiveAlarmConfigs() {
        return alarmConfigRepository.countActiveAlarms();
    }
    
    /**
     * 获取传感器通知配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getNotificationAlarmConfigsForSensor(String sensorId) {
        return alarmConfigRepository.findNotificationAlarmsForSensor(sensorId).stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取基于阈值的报警配置
     */
    @Transactional(readOnly = true)
    public List<AlarmConfigDTO> getThresholdBasedAlarmConfigs() {
        return alarmConfigRepository.findThresholdBasedAlarms().stream()
                .map(alarmConfigMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 批量更新报警配置
     */
    @Transactional
    public List<AlarmConfigDTO> batchUpdateAlarmConfigs(List<AlarmConfigDTO> dtos) {
        return dtos.stream()
                .map(dto -> {
                    if (dto.getId() == null) {
                        return createAlarmConfig(dto);
                    } else {
                        return updateAlarmConfig(dto.getId(), dto);
                    }
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 验证报警配置
     */
    public boolean validateAlarmConfig(AlarmConfigDTO dto) {
        if (dto == null) return false;
        if (dto.getName() == null || dto.getName().trim().isEmpty()) return false;
        if (dto.getAlarmType() == null || dto.getAlarmType().trim().isEmpty()) return false;
        if (dto.getConditionType() == null || dto.getConditionType().trim().isEmpty()) return false;
        if (dto.getEnabled() == null) return false;
        return dto.validateThresholds();
    }
}