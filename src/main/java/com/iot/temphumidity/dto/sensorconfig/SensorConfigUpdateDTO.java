package com.iot.temphumidity.dto.sensorconfig;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 更新传感器配置DTO
 * 所有字段都是可选的
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorConfigUpdateDTO {
    
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
    @Size(max = 255, message = "MQTT主题长度不能超过255")
    private String mqttTopic;
    
    @Size(max = 255, message = "HTTP端点长度不能超过255")
    private String httpEndpoint;
    
    // 电池配置
    private BigDecimal batteryAlarmThreshold;
    
    // 信号配置
    private Integer signalRssiAlarmThreshold;
    
    // 状态
    @Size(max = 20, message = "配置状态长度不能超过20")
    private String configStatus;
}