package com.iot.temphumidity.dto.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 传感器阈值DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorThresholdsDTO {
    
    @NotNull(message = "传感器ID不能为空")
    private String sensorId;
    
    private BigDecimal tempHighWarning;
    private BigDecimal tempHighCritical;
    private BigDecimal tempLowWarning;
    private BigDecimal tempLowCritical;
    
    private BigDecimal humidityHighWarning;
    private BigDecimal humidityHighCritical;
    private BigDecimal humidityLowWarning;
    private BigDecimal humidityLowCritical;
    
    private BigDecimal batteryLowWarning;
    private BigDecimal batteryLowCritical;
    
    private Integer rssiWeakWarning;
    private Integer rssiWeakCritical;
    
    private Integer dataIntervalWarning;
    private Integer dataIntervalCritical;
    
    private Boolean enableTempAlerts = true;
    private Boolean enableHumidityAlerts = true;
    private Boolean enableBatteryAlerts = true;
    private Boolean enableRssiAlerts = true;
    private Boolean enableIntervalAlerts = true;
    
    private Integer alertCooldownMinutes = 5;
    private Integer repeatAlertIntervalMinutes = 30;
    
    private String notificationChannels;
    private String escalationRules;
    
    private Boolean enabled = true;
}