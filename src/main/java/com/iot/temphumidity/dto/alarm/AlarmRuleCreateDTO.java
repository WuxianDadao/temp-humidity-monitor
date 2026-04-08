package com.iot.temphumidity.dto.alarm;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 创建报警规则DTO
 */
@Data
public class AlarmRuleCreateDTO {
    
    /** 规则名称 */
    @NotBlank(message = "规则名称不能为空")
    private String ruleName;
    
    /** 传感器ID */
    private String sensorId;
    
    /** 设备ID */
    private String deviceId;
    
    /** 报警类型: temperature/humidity/battery */
    @NotBlank(message = "报警类型不能为空")
    private String alarmType;
    
    /** 比较运算符: gt/lt/eq */
    @NotBlank(message = "比较运算符不能为空")
    private String operator;
    
    /** 阈值 */
    @NotNull(message = "阈值不能为空")
    private BigDecimal threshold;
    
    /** 持续时长(秒) */
    private Integer duration = 60;
    
    /** 报警级别: info/warning/error/critical */
    private String severity = "warning";
    
    /** 是否启用 */
    private Boolean enabled = true;
    
    /** 通知方式: email/sms/webhook */
    private String notifyType = "email";
    
    /** 通知接收人 */
    private String notifyReceiver;
    
    /** 描述 */
    private String description;
}