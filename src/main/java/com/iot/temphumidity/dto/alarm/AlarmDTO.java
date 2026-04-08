package com.iot.temphumidity.dto.alarm;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlarmDTO {
    private String alarmId;
    private String deviceId;
    private String sensorId;
    private String alarmType;
    private String alarmLevel;
    private String alarmMessage;
    private LocalDateTime alarmTime;
    private LocalDateTime ackTime;
    private String ackUser;
    private LocalDateTime resolveTime;
    private String resolveUser;
    private String alarmStatus;
    private Double currentValue;
    private Double thresholdValue;
    private String alarmRuleId;
}
