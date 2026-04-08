package com.iot.temphumidity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 传感器数据DTO
 */
@Data
@Schema(description = "传感器数据传输对象")
public class SensorDataDTO {
    
    @Schema(description = "时间戳", example = "2026-03-30T11:45:00")
    private LocalDateTime ts;
    
    @Schema(description = "传感器ID", example = "SENSOR_001")
    private String sensorId;
    
    @Schema(description = "设备ID", example = "DEVICE_001")
    private String deviceId;
    
    @Schema(description = "温度 (°C)", example = "25.5")
    private Double temperature;
    
    @Schema(description = "湿度 (%)", example = "60.2")
    private Double humidity;
    
    @Schema(description = "电池电量 (%)", example = "95.5")
    private Double battery;
    
    @Schema(description = "信号强度 (RSSI)", example = "-60")
    private Integer rssi;
    
    @Schema(description = "位置", example = "仓库A区-货架1")
    private String location;
    
    @Schema(description = "传感器类型", example = "温湿度传感器")
    private String sensorType;
    
    @Schema(description = "数据质量 (0-100)", example = "98.5")
    private Double dataQuality;
    
    @Schema(description = "数据来源 (MQTT, HTTP, MANUAL)", example = "MQTT")
    private String dataSource;
    
    @Schema(description = "是否报警", example = "false")
    private Boolean isAlarm;
    
    @Schema(description = "报警类型 (0:无报警, 1:温度过高, 2:温度过低, 3:湿度过高, 4:湿度过低, 5:电池电量低)", example = "0")
    private Integer alarmType;
    
    @Schema(description = "报警级别", example = "0")
    private Integer alarmLevel;
    
    @Schema(description = "报警描述", example = "")
    private String alarmDescription;
    
    @Schema(description = "原始数据 (JSON格式)", example = "{\"raw_temp\":25.5,\"raw_humidity\":60.2}")
    private String rawData;
    
    @Schema(description = "数据版本", example = "1")
    private Integer dataVersion;
    
    @Schema(description = "创建时间", example = "2026-03-30T11:45:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间", example = "2026-03-30T11:45:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "温度状态 (NORMAL:正常, HIGH:过高, LOW:过低)", example = "NORMAL")
    private String temperatureStatus;
    
    @Schema(description = "湿度状态 (NORMAL:正常, HIGH:过高, LOW:过低)", example = "NORMAL")
    private String humidityStatus;
    
    @Schema(description = "电池状态 (NORMAL:正常, LOW:电量低)", example = "NORMAL")
    private String batteryStatus;
    
    @Schema(description = "信号状态 (EXCELLENT:优秀, GOOD:良好, FAIR:一般, POOR:差)", example = "GOOD")
    private String signalStatus;
    
    @Schema(description = "自定义标签 (JSON格式)", example = "{\"zone\":\"A\",\"rack\":\"1\"}")
    private String customTags;
    
    /**
     * 统计信息
     */
    @Data
    public static class Statistics {
        @Schema(description = "平均值")
        private Double average;
        
        @Schema(description = "最小值")
        private Double min;
        
        @Schema(description = "最大值")
        private Double max;
        
        @Schema(description = "数据点数")
        private Long count;
        
        @Schema(description = "标准差")
        private Double stdDev;
        
        @Schema(description = "总和")
        private Double sum;
    }
    
    /**
     * 时间范围
     */
    @Data
    public static class TimeRange {
        @Schema(description = "开始时间")
        private LocalDateTime startTime;
        
        @Schema(description = "结束时间")
        private LocalDateTime endTime;
        
        @Schema(description = "时间粒度 (HOUR, DAY, WEEK, MONTH)")
        private String granularity;
    }
}