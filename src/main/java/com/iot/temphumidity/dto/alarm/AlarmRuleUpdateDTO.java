package com.iot.temphumidity.dto.alarm;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 更新报警规则DTO
 */
@Data
public class AlarmRuleUpdateDTO {
    
    /** 规则名称 */
    private String ruleName;
    
    /** 比较运算符: gt/lt/eq */
    private String operator;
    
    /** 阈值 */
    private BigDecimal threshold;
    
    /** 持续时长(秒) */
    private Integer duration;
    
    /** 报警级别: info/warning/error/critical */
    private String severity;
    
    /** 是否启用 */
    private Boolean enabled;
    
    /** 通知方式: email/sms/webhook */
    private String notifyType;
    
    /** 通知接收人 */
    private String notifyReceiver;
    
    /** 描述 */
    private String description;
}