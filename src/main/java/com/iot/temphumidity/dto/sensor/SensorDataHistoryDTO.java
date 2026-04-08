package com.iot.temphumidity.dto.sensor;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 传感器历史数据DTO
 * 用于传输传感器历史数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataHistoryDTO {
    
    /**
     * 时间戳
     */
    private LocalDateTime timestamp;
    
    /**
     * 温度值
     */
    private Double temperature;
    
    /**
     * 湿度值
     */
    private Double humidity;
    
    /**
     * 电池电量
     */
    private Double battery;
    
    /**
     * 信号强度
     */
    private Integer rssi;
    
    /**
     * 传感器ID
     */
    private String sensorId;
    
    /**
     * 设备ID
     */
    private String deviceId;
    
    /**
     * 位置信息
     */
    private String location;
    
    /**
     * 传感器类型
     */
    private String sensorType;
    
    /**
     * 是否聚合查询
     */
    private Boolean aggregate;
    
    /**
     * 聚合函数 (AVG, SUM, MAX, MIN, COUNT)
     */
    private String aggregationFunction;
    
    /**
     * 传感器标签ID
     */
    private Long sensorTagId;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 聚合间隔（分钟）
     */
    private Integer intervalMinutes;
    
    /**
     * 排序方式 (ASC/DESC)
     */
    private String orderBy;
}