package com.iot.temphumidity.dto.sensor;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 传感器实时数据传输对象
 * 用于传输传感器实时数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataRealtimeDTO {
    
    /**
     * 传感器ID
     */
    private String sensorId;
    
    /**
     * 设备ID
     */
    private String deviceId;
    
    /**
     * 温度值（摄氏度）
     */
    private Double temperature;
    
    /**
     * 湿度值（百分比）
     */
    private Double humidity;
    
    /**
     * 电池电量（百分比）
     */
    private Double battery;
    
    /**
     * 信号强度（RSSI）
     */
    private Integer rssi;
    
    /**
     * 数据采集时间
     */
    private LocalDateTime timestamp;
    
    /**
     * 网关ID
     */
    private String gatewayId;
    
    /**
     * 位置信息
     */
    private String location;
    
    /**
     * 传感器类型
     */
    private String sensorType;
    
    /**
     * 数据质量（0-100）
     */
    private Integer dataQuality;
    
    /**
     * 是否报警
     */
    private Boolean alarm;
    
    /**
     * 报警类型
     */
    private String alarmType;
    
    /**
     * 报警级别
     */
    private String alarmLevel;
    
    /**
     * 报警描述
     */
    private String alarmDescription;
    
    /**
     * 数据来源（MQTT/HTTP/API等）
     */
    private String dataSource;
    
    /**
     * 数据版本
     */
    private Integer dataVersion;
    
    /**
     * 扩展字段（JSON格式）
     */
    private String extraFields;
    
    /**
     * 传感器标签ID
     */
    private Long sensorTagId;
    
    /**
     * 最新时间戳
     */
    private LocalDateTime latestTimestamp;
    
    /**
     * 是否在线
     */
    private Boolean isOnline;
    
    /**
     * 最后在线时间
     */
    private LocalDateTime lastOnlineTime;
    
    /**
     * 最新温度（用于查询结果）
     */
    private Double latestTemperature;
    
    /**
     * 最新湿度（用于查询结果）
     */
    private Double latestHumidity;
    
    /**
     * 最新电池电量（用于查询结果）
     */
    private Double latestBattery;
    
    /**
     * 最新信号强度（用于查询结果）
     */
    private Integer latestRssi;
    
    /**
     * 设置最新温度
     */
    public void setLatestTemperature(Double latestTemperature) {
        this.latestTemperature = latestTemperature;
    }
    
    /**
     * 设置最新湿度
     */
    public void setLatestHumidity(Double latestHumidity) {
        this.latestHumidity = latestHumidity;
    }
    
    /**
     * 设置最新电池电量
     */
    public void setLatestBattery(Double latestBattery) {
        this.latestBattery = latestBattery;
    }
    
    /**
     * 设置最新信号强度
     */
    public void setLatestRssi(Integer latestRssi) {
        this.latestRssi = latestRssi;
    }
}