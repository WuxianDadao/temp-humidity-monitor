package com.iot.temphumidity.dto.alarm;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class AlarmRuleDTO {
    private String ruleId;
    
    @NotNull(message = "设备ID不能为空")
    private String deviceId;
    
    private String sensorId;
    
    @NotNull(message = "告警类型不能为空")
    private String alarmType;
    
    @NotNull(message = "比较运算符不能为空")
    private String operator;  // >, <, >=, <=, ==, !=
    
    @NotNull(message = "阈值不能为空")
    private Double threshold;
    
    @NotNull(message = "告警级别不能为空")
    private String alarmLevel;  // CRITICAL, WARNING, INFO
    
    private String alarmMessage;
    private Boolean enabled;
    private Integer duration;  // 持续时间（秒）
    private Integer repeatInterval;  // 重复告警间隔
}
