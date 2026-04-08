package com.iot.temphumidity.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 传感器统计DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorStatisticsDTO {
    
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    private String statisticsType;
    private String timeRange;
    
    private BigDecimal temperatureStdDev;
    private BigDecimal humidityStdDev;
    
    private Integer alarmCount;
    private Integer warningCount;
    private Integer normalCount;
    
    private BigDecimal dataQuality;
    private BigDecimal uptimeRate;
}