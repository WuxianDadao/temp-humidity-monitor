package com.iot.temphumidity.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 传感器统计DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorStatsDTO {
    
    private String sensorId;
    private String sensorName;
    private String deviceId;
    private String location;
    
    private BigDecimal avgTemperature;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
    
    private BigDecimal avgHumidity;
    private BigDecimal maxHumidity;
    private BigDecimal minHumidity;
    
    private BigDecimal avgBattery;
    private BigDecimal minBattery;
    
    private Integer dataCount;
    
    // 用于统计的字段
    private Long durationHours;
    private String statisticsPeriod;
    
    // 报警统计
    private Integer alarmCount;
    private Integer warningCount;
    private Integer normalCount;
    
    // 性能指标
    private BigDecimal dataQuality;
    private BigDecimal uptimeRate;
    
    // 趋势指标
    private BigDecimal temperatureTrend;
    private BigDecimal humidityTrend;
    private BigDecimal batteryTrend;
}