package com.iot.temphumidity.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 报警统计DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmStatsDTO {
    
    private String period;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    private Integer totalAlarms;
    private Integer pendingAlarms;
    private Integer acknowledgedAlarms;
    private Integer resolvedAlarms;
    
    private Integer infoAlarms;
    private Integer warningAlarms;
    private Integer minorAlarms;
    private Integer majorAlarms;
    private Integer criticalAlarms;
    
    private Integer temperatureAlarms;
    private Integer humidityAlarms;
    private Integer batteryAlarms;
    private Integer offlineAlarms;
    private Integer dataErrorAlarms;
    
    private Double avgResponseTimeMinutes;
    private Double avgResolutionTimeMinutes;
    
    private Integer falseAlarms;
    private Integer repeatedAlarms;
    
    private String topAlertDeviceId;
    private Integer topAlertDeviceCount;
    
    private String topAlertSensorId;
    private Integer topAlertSensorCount;
    
    private Double alarmRatePerDay;
    private Double resolutionRate;
    
    private String trend;
}