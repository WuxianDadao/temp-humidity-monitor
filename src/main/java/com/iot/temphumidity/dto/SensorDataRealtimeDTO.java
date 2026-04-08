package com.iot.temphumidity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SensorDataRealtimeDTO {
    private Long sensorTagId;
    private String sensorName;
    private LocalDateTime latestTimestamp;
    private Double latestTemperature;
    private Double latestHumidity;
    private Double latestBattery;
    private Integer latestRssi;
    private String trend;
    private Double temperatureTrend;
    private Double humidityTrend;
    private Boolean isOnline;
    private LocalDateTime lastOnlineTime;
    private Integer offlineMinutes;
    private Boolean hasAlarm;
    private String alarmLevel;
    
    // 传感器基本信息
    private String sensorId;
    private String deviceId;
    private String location;
    private String sensorType;
    
    // 网关信息
    private String gatewayName;
    private String gatewayStatus;
    
    // RSSI信号强度
    private Integer rssi;
}