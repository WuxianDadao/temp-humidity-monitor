package com.iot.temphumidity.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 每小时统计数据DTO（SensorTagService接口需要）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourlyStatsDTO {
    
    /**
     * 传感器ID（字符串格式）
     */
    private String sensorId;
    
    /**
     * 统计小时（开始时间）
     */
    private LocalDateTime hourStart;
    
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
     * 平均电池电量
     */
    private BigDecimal avgBattery;
    
    /**
     * 平均信号强度
     */
    private BigDecimal avgRssi;
    
    /**
     * 报警次数
     */
    private Integer alarmCount;
    
    /**
     * 数据接收成功率
     */
    private BigDecimal dataReceiptRate;
    
    /**
     * 温度变化趋势（相对于前一小时）
     */
    private BigDecimal temperatureTrend;
    
    /**
     * 湿度变化趋势（相对于前一小时）
     */
    private BigDecimal humidityTrend;
}