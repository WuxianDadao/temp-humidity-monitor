package com.iot.temphumidity.dto.sensorconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 传感器配置DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorConfigDTO {
    
    private Long id;
    private Long sensorTagId;
    private Long deviceId;
    private Long gatewayId;
    
    // 温度报警配置
    private Boolean temperatureAlarmEnabled;
    private BigDecimal temperatureAlarmThresholdMin;
    private BigDecimal temperatureAlarmThresholdMax;
    
    // 湿度报警配置
    private Boolean humidityAlarmEnabled;
    private BigDecimal humidityAlarmThresholdMin;
    private BigDecimal humidityAlarmThresholdMax;
    
    // 采样配置
    private Integer samplingIntervalSeconds;
    private Integer dataRetentionDays;
    
    // 网络配置
    private String mqttTopic;
    private String httpEndpoint;
    
    // 电池配置
    private BigDecimal batteryAlarmThreshold;
    
    // 信号配置
    private Integer signalRssiAlarmThreshold;
    
    // 状态
    private String configStatus;
    private LocalDateTime lastConfigSyncTime;
    private LocalDateTime lastDataReceiveTime;
    
    // 时间戳
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    
    // 关联信息
    private String sensorTagName;
    private String deviceName;
    private String gatewayName;
}