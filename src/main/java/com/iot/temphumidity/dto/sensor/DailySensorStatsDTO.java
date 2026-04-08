package com.iot.temphumidity.dto.sensor;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 传感器日统计DTO
 * 用于传输传感器一天的统计信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySensorStatsDTO {
    
    /**
     * 传感器ID
     */
    private Long sensorId;
    
    /**
     * 日期
     */
    private LocalDate date;
    
    /**
     * 平均温度 (°C)
     */
    private Double avgTemperature;
    
    /**
     * 最低温度 (°C)
     */
    private Double minTemperature;
    
    /**
     * 最高温度 (°C)
     */
    private Double maxTemperature;
    
    /**
     * 平均湿度 (%)
     */
    private Double avgHumidity;
    
    /**
     * 最低湿度 (%)
     */
    private Double minHumidity;
    
    /**
     * 最高湿度 (%)
     */
    private Double maxHumidity;
    
    /**
     * 平均电池电量 (%)
     */
    private Double avgBattery;
    
    /**
     * 最低电池电量 (%)
     */
    private Double minBattery;
    
    /**
     * 平均信号强度 (RSSI)
     */
    private Double avgRssi;
    
    /**
     * 数据点数
     */
    private Integer dataCount;
    
    /**
     * 报警次数
     */
    private Integer alarmCount;
    
    /**
     * 温度趋势 (STABLE:稳定, RISING:上升, FALLING:下降)
     */
    private String temperatureTrend;
    
    /**
     * 湿度趋势 (STABLE:稳定, RISING:上升, FALLING:下降)
     */
    private String humidityTrend;
    
    /**
     * 电池预计剩余天数
     */
    private Integer batteryDaysLeft;
}