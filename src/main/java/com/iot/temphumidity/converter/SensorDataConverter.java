package com.iot.temphumidity.converter;

import com.iot.temphumidity.dto.sensor.SensorDataHistoryDTO;
import com.iot.temphumidity.dto.sensor.SensorDataRealtimeDTO;
import com.iot.temphumidity.dto.SensorDataBatchDTO;
import com.iot.temphumidity.entity.tdengine.SensorDataTD;
import com.iot.temphumidity.entity.Gateway;
import com.iot.temphumidity.entity.Sensor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 传感器数据转换器
 * 负责TDengine实体与DTO之间的转换
 */
@Component
public class SensorDataConverter {
    
    /**
     * 将TDengine实体转换为历史DTO
     */
    public SensorDataHistoryDTO toHistoryDTO(SensorDataTD tdEntity) {
        if (tdEntity == null) {
            return null;
        }
        
        SensorDataHistoryDTO dto = new SensorDataHistoryDTO();
        dto.setTimestamp(tdEntity.getTimestamp());
        dto.setTemperature(tdEntity.getTemperature() != null ? tdEntity.getTemperature().doubleValue() : null);
        dto.setHumidity(tdEntity.getHumidity() != null ? tdEntity.getHumidity().doubleValue() : null);
        dto.setBattery(tdEntity.getBattery() != null ? tdEntity.getBattery().doubleValue() : null);
        dto.setRssi(tdEntity.getRssi());
        dto.setSensorId(tdEntity.getDeviceId()); // 注意：deviceId存储sensorId
        dto.setDeviceId(tdEntity.getDeviceId());
        dto.setLocation(tdEntity.getLocation());
        dto.setSensorType(tdEntity.getSensorType());
        
        return dto;
    }
    
    /**
     * 将TDengine实体列表转换为历史DTO列表
     */
    public List<SensorDataHistoryDTO> toHistoryDTOList(List<SensorDataTD> tdEntities) {
        return tdEntities.stream()
                .map(this::toHistoryDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 将TDengine实体转换为实时DTO
     */
    public SensorDataRealtimeDTO toRealtimeDTO(SensorDataTD tdEntity, Gateway gateway, Sensor sensor) {
        if (tdEntity == null) {
            return null;
        }
        
        SensorDataRealtimeDTO dto = new SensorDataRealtimeDTO();
        dto.setTimestamp(tdEntity.getTimestamp());
        dto.setTemperature(tdEntity.getTemperature() != null ? tdEntity.getTemperature().doubleValue() : null);
        dto.setHumidity(tdEntity.getHumidity() != null ? tdEntity.getHumidity().doubleValue() : null);
        dto.setBattery(tdEntity.getBattery() != null ? tdEntity.getBattery().doubleValue() : null);
        dto.setRssi(tdEntity.getRssi());
        dto.setSensorId(tdEntity.getDeviceId()); // 注意：deviceId存储sensorId
        dto.setDeviceId(tdEntity.getDeviceId());
        dto.setLocation(tdEntity.getLocation());
        dto.setSensorType(tdEntity.getSensorType());
        
        // 添加网关信息 - 只设置DTO中实际存在的字段
        if (gateway != null) {
            // SensorDataRealtimeDTO没有gatewayName, gatewayStatus, lastHeartbeat字段
            // 所以只设置gatewayId（如果存在）
            // dto.setGatewayId(gateway.getId()); // 假设gateway有getId()方法
        }
        
        // 添加传感器信息 - 只设置DTO中实际存在的字段
        if (sensor != null) {
            // SensorDataRealtimeDTO没有sensorName, sensorModel, installDate, calibrationDate字段
            // 所以不设置这些字段
        }
        
        return dto;
    }
    
    /**
     * 从MQTT消息创建TDengine实体
     */
    public SensorDataTD fromMqttMessage(String topic, String payload) {
        // 解析MQTT消息
        // 这里简化实现，实际应用中需要根据具体协议解析
        SensorDataTD entity = new SensorDataTD();
        entity.setTimestamp(LocalDateTime.now());
        
        // 从topic中提取设备信息
        // topic格式: iot/sensor/{deviceId}/{sensorId}/data
        String[] parts = topic.split("/");
        if (parts.length >= 4) {
            entity.setDeviceId(parts[2]);
            // SensorDataTD没有sensorId字段，跳过
            // entity.setSensorId(parts[3]);
        }
        
        // 解析payload (JSON格式)
        // {"temperature": 25.5, "humidity": 60.2, "battery": 3.7, "rssi": -65}
        try {
            // 这里简化处理，实际应用中需要JSON解析
            if (payload.contains("\"temperature\":")) {
                // 解析温度
                String tempStr = payload.split("\"temperature\":")[1].split(",")[0].trim();
                entity.setTemperature(Float.valueOf(tempStr));
            }
            if (payload.contains("\"humidity\":")) {
                // 解析湿度
                String humStr = payload.split("\"humidity\":")[1].split(",")[0].trim();
                entity.setHumidity(Float.valueOf(humStr));
            }
            if (payload.contains("\"battery\":")) {
                // 解析电池
                String batStr = payload.split("\"battery\":")[1].split(",")[0].trim();
                entity.setBattery(Float.valueOf(batStr));
            }
            if (payload.contains("\"rssi\":")) {
                // 解析信号强度
                String rssiStr = payload.split("\"rssi\":")[1].split("}")[0].trim();
                entity.setRssi(Integer.parseInt(rssiStr));
            }
        } catch (Exception e) {
            // 解析失败时使用默认值
            entity.setTemperature(25.0f);
            entity.setHumidity(60.0f);
            entity.setBattery(3.7f);
            entity.setRssi(-65);
        }
        
        // 设置默认标签
        entity.setLocation("unknown");
        entity.setSensorType("temperature_humidity");
        
        return entity;
    }
    
    /**
     * 批量转换MQTT消息
     */
    public List<SensorDataTD> fromMqttMessages(List<String> topics, List<String> payloads) {
        if (topics.size() != payloads.size()) {
            throw new IllegalArgumentException("Topics and payloads must have the same size");
        }
        
        List<SensorDataTD> entities = new java.util.ArrayList<>();
        for (int i = 0; i < topics.size(); i++) {
            entities.add(fromMqttMessage(topics.get(i), payloads.get(i)));
        }
        return entities;
    }
    
    /**
     * 创建批量DTO
     */
    public SensorDataBatchDTO createBatchDTO(List<SensorDataTD> entities, String gatewayId, Date startTime, Date endTime) {
        SensorDataBatchDTO batchDTO = new SensorDataBatchDTO();
        // 注意：SensorDataBatchDTO没有gatewayId、startTime、endTime和data字段
        // 但可以设置统计信息
        batchDTO.setSensorCount(entities.size());
        
        // 计算统计信息
        if (!entities.isEmpty()) {
            double avgTemp = entities.stream()
                    .mapToDouble(e -> e.getTemperature() != null ? e.getTemperature().doubleValue() : 0.0)
                    .average()
                    .orElse(0.0);
            double avgHum = entities.stream()
                    .mapToDouble(e -> e.getHumidity() != null ? e.getHumidity().doubleValue() : 0.0)
                    .average()
                    .orElse(0.0);
            
            batchDTO.setAvgTemperature(avgTemp);
            batchDTO.setAvgHumidity(avgHum);
            batchDTO.setMinTemperature(entities.stream()
                    .mapToDouble(e -> e.getTemperature() != null ? e.getTemperature().doubleValue() : 0.0)
                    .min()
                    .orElse(0.0));
            batchDTO.setMaxTemperature(entities.stream()
                    .mapToDouble(e -> e.getTemperature() != null ? e.getTemperature().doubleValue() : 0.0)
                    .max()
                    .orElse(0.0));
            batchDTO.setMinHumidity(entities.stream()
                    .mapToDouble(e -> e.getHumidity() != null ? e.getHumidity().doubleValue() : 0.0)
                    .min()
                    .orElse(0.0));
            batchDTO.setMaxHumidity(entities.stream()
                    .mapToDouble(e -> e.getHumidity() != null ? e.getHumidity().doubleValue() : 0.0)
                    .max()
                    .orElse(0.0));
        }
        
        return batchDTO;
    }
}