package com.iot.temphumidity.dto.sensorconfig;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 创建传感器配置DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorConfigCreateDTO {
    
    @NotNull(message = "传感器标签ID不能为空")
    private Long sensorTagId;
    
    private Long deviceId;
    private Long gatewayId;
    
    // 温度报警配置
    private Boolean temperatureAlarmEnabled = true;
    private BigDecimal temperatureAlarmThresholdMin = new BigDecimal("-20.00");
    private BigDecimal temperatureAlarmThresholdMax = new BigDecimal("80.00");
    
    // 湿度报警配置
    private Boolean humidityAlarmEnabled = true;
    private BigDecimal humidityAlarmThresholdMin = new BigDecimal("0.00");
    private BigDecimal humidityAlarmThresholdMax = new BigDecimal("100.00");
    
    // 采样配置
    private Integer samplingIntervalSeconds = 60;
    private Integer dataRetentionDays = 365;
    
    // 网络配置
    private String mqttTopic;
    private String httpEndpoint;
    
    // 电池配置
    private BigDecimal batteryAlarmThreshold = new BigDecimal("20.00");
    
    // 信号配置
    private Integer signalRssiAlarmThreshold = -80;
    
    // 状态
    private String configStatus = "ACTIVE";
}