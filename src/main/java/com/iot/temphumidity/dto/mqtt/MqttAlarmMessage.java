package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MqttAlarmMessage {
    private String alarmId;
    private String deviceId;
    private String sensorId;
    private String alarmType;
    private String alarmLevel;
    private String alarmMessage;
    private LocalDateTime alarmTime;
    private Double currentValue;
    private Double thresholdValue;
    private String alarmRuleId;
}
