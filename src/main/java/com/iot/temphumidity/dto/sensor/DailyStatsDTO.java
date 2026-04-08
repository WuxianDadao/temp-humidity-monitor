package com.iot.temphumidity.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日统计数据DTO（SensorTagService接口需要）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyStatsDTO {
    
    /**
     * 传感器ID（字符串格式）
     */
    private String sensorId;
    
    /**
     * 统计日期
     */
    private LocalDate statDate;
    
    /**
     * 数据点数量
     */
    private Integer dataCount;
    
    /**
     * 平均温度
     */
    private BigDecimal avgTemperature;
    
    /**
     * 最高温度
     */
    private BigDecimal maxTemperature;
    
    /**
     * 最低温度
     */
    private BigDecimal minTemperature;
    
    /**
     * 温度标准差
     */
    private BigDecimal temperatureStdDev;
    
    /**
     * 平均湿度
     */
    private BigDecimal avgHumidity;
    
    /**
     * 最高湿度
     */
    private BigDecimal maxHumidity;
    
    /**
     * 最低湿度
     */
    private BigDecimal minHumidity;
    
    /**
     * 湿度标准差
     */
    private BigDecimal humidityStdDev;
    
    /**
     * 平均电池电量
     */
    private BigDecimal avgBattery;
    
    /**
     * 最低电池电量
     */
    private BigDecimal minBattery;
    
    /**
     * 平均信号强度
     */
    private BigDecimal avgRssi;
    
    /**
     * 报警数量
     */
    private Integer alarmCount;
    
    /**
     * 离线时长（分钟）
     */
    private Integer offlineMinutes;
    
    /**
     * 数据质量评分（0-100）
     */
    private BigDecimal dataQualityScore;
}