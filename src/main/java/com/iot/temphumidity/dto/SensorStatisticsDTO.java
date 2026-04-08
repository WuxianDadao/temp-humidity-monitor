package com.iot.temphumidity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 传感器统计信息DTO
 */
@Data
@Schema(description = "传感器统计信息")
public class SensorStatisticsDTO {
    
    @Schema(description = "传感器ID", example = "SENSOR_001")
    private String sensorId;
    
    @Schema(description = "设备ID", example = "DEVICE_001")
    private String deviceId;
    
    @Schema(description = "统计类型 (TEMPERATURE, HUMIDITY, BATTERY, RSSI)", example = "TEMPERATURE")
    private String statType;
    
    @Schema(description = "平均值", example = "25.5")
    private Double average;
    
    @Schema(description = "最小值", example = "22.3")
    private Double min;
    
    @Schema(description = "最大值", example = "28.7")
    private Double max;
    
    @Schema(description = "数据点数", example = "100")
    private Long count;
    
    @Schema(description = "标准差", example = "1.2")
    private Double stdDev;
    
    @Schema(description = "总和", example = "2550.0")
    private Double sum;
    
    @Schema(description = "开始时间", example = "2026-03-30T00:00:00")
    private String startTime;
    
    @Schema(description = "结束时间", example = "2026-03-30T23:59:59")
    private String endTime;
    
    @Schema(description = "时间粒度 (HOUR, DAY, WEEK, MONTH)", example = "DAY")
    private String granularity;
    
    @Schema(description = "温度统计 (当统计类型为TEMPERATURE时)")
    private TemperatureStats temperatureStats;
    
    @Schema(description = "湿度统计 (当统计类型为HUMIDITY时)")
    private HumidityStats humidityStats;
    
    @Schema(description = "电池统计 (当统计类型为BATTERY时)")
    private BatteryStats batteryStats;
    
    @Schema(description = "信号统计 (当统计类型为RSSI时)")
    private RssiStats rssiStats;
    
    /**
     * 温度统计详情
     */
    @Data
    public static class TemperatureStats {
        @Schema(description = "平均温度 (°C)")
        private Double avgTemp;
        
        @Schema(description = "最低温度 (°C)")
        private Double minTemp;
        
        @Schema(description = "最高温度 (°C)")
        private Double maxTemp;
        
        @Schema(description = "温度变化趋势 (STABLE:稳定, RISING:上升, FALLING:下降)")
        private String trend;
        
        @Schema(description = "温度报警次数")
        private Integer alarmCount;
    }
    
    /**
     * 湿度统计详情
     */
    @Data
    public static class HumidityStats {
        @Schema(description = "平均湿度 (%)")
        private Double avgHumidity;
        
        @Schema(description = "最低湿度 (%)")
        private Double minHumidity;
        
        @Schema(description = "最高湿度 (%)")
        private Double maxHumidity;
        
        @Schema(description = "湿度变化趋势 (STABLE:稳定, RISING:上升, FALLING:下降)")
        private String trend;
        
        @Schema(description = "湿度报警次数")
        private Integer alarmCount;
    }
    
    /**
     * 电池统计详情
     */
    @Data
    public static class BatteryStats {
        @Schema(description = "平均电量 (%)")
        private Double avgBattery;
        
        @Schema(description = "最低电量 (%)")
        private Double minBattery;
        
        @Schema(description = "预计剩余天数")
        private Integer estimatedDaysLeft;
        
        @Schema(description = "电池状态 (GOOD:良好, WARNING:警告, CRITICAL:严重)")
        private String status;
        
        @Schema(description = "低电量报警次数")
        private Integer lowBatteryAlarms;
    }
    
    /**
     * 信号统计详情
     */
    @Data
    public static class RssiStats {
        @Schema(description = "平均信号强度 (RSSI)")
        private Double avgRssi;
        
        @Schema(description = "最弱信号 (RSSI)")
        private Double minRssi;
        
        @Schema(description = "最强信号 (RSSI)")
        private Double maxRssi;
        
        @Schema(description = "信号稳定性 (百分比)")
        private Double stability;
        
        @Schema(description = "连接中断次数")
        private Integer disconnectionCount;
    }
}