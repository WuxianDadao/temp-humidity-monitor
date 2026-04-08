package com.iot.temphumidity.dto.sensorconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 传感器配置同步DTO
 * 用于向设备同步配置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorConfigSyncDTO {
    
    private Long configId;
    private Long sensorTagId;
    private Long deviceId;
    private Long gatewayId;
    
    // 温度配置
    private Boolean temperatureAlarmEnabled;
    private BigDecimal temperatureAlarmThresholdMin;
    private BigDecimal temperatureAlarmThresholdMax;
    
    // 湿度配置
    private Boolean humidityAlarmEnabled;
    private BigDecimal humidityAlarmThresholdMin;
    private BigDecimal humidityAlarmThresholdMax;
    
    // 采样配置（秒）
    private Integer samplingIntervalSeconds;
    
    // MQTT主题
    private String mqttTopic;
    
    // HTTP端点（用于设备拉取配置）
    private String httpEndpoint;
    
    // 电池报警阈值
    private BigDecimal batteryAlarmThreshold;
    
    // 信号强度报警阈值
    private Integer signalRssiAlarmThreshold;
    
    // 同步状态
    private String syncStatus = "PENDING";
    private String syncMessage;
    
    // 版本信息
    private String configVersion = "1.0";
    private Long syncTimestamp;
    
    /**
     * 转换为JSON格式，用于设备同步
     */
    public String toDeviceJson() {
        return String.format(
            "{\"configId\":%d,\"sensorTagId\":%d,\"tempAlarm\":%s,\"tempMin\":%.2f,\"tempMax\":%.2f," +
            "\"humidityAlarm\":%s,\"humidityMin\":%.2f,\"humidityMax\":%.2f," +
            "\"interval\":%d,\"mqttTopic\":\"%s\",\"batteryThreshold\":%.2f," +
            "\"rssiThreshold\":%d,\"version\":\"%s\",\"timestamp\":%d}",
            configId, sensorTagId, temperatureAlarmEnabled, 
            temperatureAlarmThresholdMin != null ? temperatureAlarmThresholdMin.doubleValue() : -20.0,
            temperatureAlarmThresholdMax != null ? temperatureAlarmThresholdMax.doubleValue() : 80.0,
            humidityAlarmEnabled, humidityAlarmThresholdMin != null ? humidityAlarmThresholdMin.doubleValue() : 0.0,
            humidityAlarmThresholdMax != null ? humidityAlarmThresholdMax.doubleValue() : 100.0,
            samplingIntervalSeconds, mqttTopic != null ? mqttTopic : "",
            batteryAlarmThreshold != null ? batteryAlarmThreshold.doubleValue() : 20.0,
            signalRssiAlarmThreshold != null ? signalRssiAlarmThreshold : -80,
            configVersion, System.currentTimeMillis() / 1000
        );
    }
}