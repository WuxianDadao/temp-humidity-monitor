package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class MqttAlarmStatistics {
    private String deviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Map<String, Integer> alarmCountByType;
    private Map<String, Integer> alarmCountByLevel;
    private Map<String, Integer> alarmCountBySensor;
    private Integer totalAlarms;
    private Integer criticalCount;
    private Integer warningCount;
    private Integer infoCount;
}
