package com.iot.temphumidity.dto;

import com.iot.temphumidity.entity.AlarmRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 报警规则更新数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRuleUpdateDTO {
    
    /**
     * 规则名称
     */
    @NotBlank(message = "规则名称不能为空")
    @Size(min = 1, max = 128, message = "规则名称长度必须在1-128个字符之间")
    private String ruleName;
    
    /**
     * 规则描述
     */
    @Size(max = 500, message = "规则描述长度不能超过500个字符")
    private String description;
    
    /**
     * 条件类型
     */
    @Size(max = 32, message = "条件类型长度不能超过32个字符")
    private String conditionType;
    
    /**
     * 条件表达式
     */
    private String conditionExpression;
    
    /**
     * 温度阈值-最小值
     */
    private Double thresholdMinTemp;
    
    /**
     * 温度阈值-最大值
     */
    private Double thresholdMaxTemp;
    
    /**
     * 湿度阈值-最小值
     */
    private Double thresholdMinHumidity;
    
    /**
     * 湿度阈值-最大值
     */
    private Double thresholdMaxHumidity;
    
    /**
     * 持续时间（秒）
     */
    private Integer durationSeconds;
    
    /**
     * 冷却时间（秒）
     */
    private Integer cooldownSeconds;
    
    /**
     * 严重性
     */
    private AlarmRule.AlarmSeverity severity;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 通知渠道
     */
    @Size(max = 255, message = "通知渠道长度不能超过255个字符")
    private String notificationChannels;
    
    /**
     * 通知模板
     */
    private String notificationTemplate;
    
    /**
     * 升级策略
     */
    private String escalationPolicy;
    
    /**
     * 是否自动确认
     */
    private Boolean autoAcknowledge;
    
    /**
     * 是否自动解决
     */
    private Boolean autoResolve;
    
    /**
     * 标签
     */
    @Size(max = 255, message = "标签长度不能超过255个字符")
    private String tags;
    
    /**
     * 验证温度阈值
     */
    public boolean validateTemperatureThresholds() {
        if (thresholdMinTemp != null && thresholdMaxTemp != null) {
            return thresholdMinTemp <= thresholdMaxTemp;
        }
        return true;
    }
    
    /**
     * 验证湿度阈值
     */
    public boolean validateHumidityThresholds() {
        if (thresholdMinHumidity != null && thresholdMaxHumidity != null) {
            return thresholdMinHumidity <= thresholdMaxHumidity;
        }
        return true;
    }
    
    /**
     * 验证冷却时间
     */
    public boolean validateCooldownSeconds() {
        if (cooldownSeconds != null) {
            return cooldownSeconds >= 0;
        }
        return true;
    }
    
    /**
     * 验证持续时间
     */
    public boolean validateDurationSeconds() {
        if (durationSeconds != null) {
            return durationSeconds >= 0;
        }
        return true;
    }
    
    /**
     * 获取验证错误信息
     */
    public String getValidationError() {
        if (!validateTemperatureThresholds()) {
            return "温度最小值不能大于最大值";
        }
        if (!validateHumidityThresholds()) {
            return "湿度最小值不能大于最大值";
        }
        if (!validateCooldownSeconds()) {
            return "冷却时间不能为负数";
        }
        if (!validateDurationSeconds()) {
            return "持续时间不能为负数";
        }
        return null;
    }
    
    /**
     * 应用更新到实体（部分更新）
     */
    public void applyToEntity(AlarmRule entity) {
        if (entity == null) {
            return;
        }
        
        if (this.ruleName != null) {
            entity.setRuleName(this.ruleName);
        }
        if (this.description != null) {
            entity.setDescription(this.description);
        }
        if (this.conditionType != null) {
            entity.setConditionType(this.conditionType);
        }
        if (this.conditionExpression != null) {
            entity.setConditionExpression(this.conditionExpression);
        }
        if (this.thresholdMinTemp != null) {
            entity.setThresholdMinTemp(this.thresholdMinTemp);
        }
        if (this.thresholdMaxTemp != null) {
            entity.setThresholdMaxTemp(this.thresholdMaxTemp);
        }
        if (this.thresholdMinHumidity != null) {
            entity.setThresholdMinHumidity(this.thresholdMinHumidity);
        }
        if (this.thresholdMaxHumidity != null) {
            entity.setThresholdMaxHumidity(this.thresholdMaxHumidity);
        }
        if (this.durationSeconds != null) {
            entity.setDurationSeconds(this.durationSeconds);
        }
        if (this.cooldownSeconds != null) {
            entity.setCooldownSeconds(this.cooldownSeconds);
        }
        if (this.severity != null) {
            entity.setSeverity(this.severity);
        }
        if (this.enabled != null) {
            entity.setEnabled(this.enabled);
        }
        if (this.notificationChannels != null) {
            entity.setNotificationChannels(this.notificationChannels);
        }
        if (this.notificationTemplate != null) {
            entity.setNotificationTemplate(this.notificationTemplate);
        }
        if (this.escalationPolicy != null) {
            entity.setEscalationPolicy(this.escalationPolicy);
        }
        if (this.autoAcknowledge != null) {
            entity.setAutoAcknowledge(this.autoAcknowledge);
        }
        if (this.autoResolve != null) {
            entity.setAutoResolve(this.autoResolve);
        }
        if (this.tags != null) {
            entity.setTags(this.tags);
        }
    }
    
    /**
     * 检查是否有任何字段被更新
     */
    public boolean hasUpdates() {
        return ruleName != null || 
               description != null || 
               conditionType != null || 
               conditionExpression != null ||
               thresholdMinTemp != null ||
               thresholdMaxTemp != null ||
               thresholdMinHumidity != null ||
               thresholdMaxHumidity != null ||
               durationSeconds != null ||
               cooldownSeconds != null ||
               severity != null ||
               enabled != null ||
               notificationChannels != null ||
               notificationTemplate != null ||
               escalationPolicy != null ||
               autoAcknowledge != null ||
               autoResolve != null ||
               tags != null;
    }
    
    /**
     * 获取更新摘要
     */
    public String getUpdateSummary() {
        StringBuilder summary = new StringBuilder();
        
        if (ruleName != null) summary.append("规则名称, ");
        if (description != null) summary.append("描述, ");
        if (conditionType != null) summary.append("条件类型, ");
        if (conditionExpression != null) summary.append("条件表达式, ");
        if (thresholdMinTemp != null || thresholdMaxTemp != null) summary.append("温度阈值, ");
        if (thresholdMinHumidity != null || thresholdMaxHumidity != null) summary.append("湿度阈值, ");
        if (durationSeconds != null) summary.append("持续时间, ");
        if (cooldownSeconds != null) summary.append("冷却时间, ");
        if (severity != null) summary.append("严重性, ");
        if (enabled != null) summary.append("启用状态, ");
        if (notificationChannels != null) summary.append("通知渠道, ");
        if (notificationTemplate != null) summary.append("通知模板, ");
        if (escalationPolicy != null) summary.append("升级策略, ");
        if (autoAcknowledge != null) summary.append("自动确认, ");
        if (autoResolve != null) summary.append("自动解决, ");
        if (tags != null) summary.append("标签, ");
        
        if (summary.length() > 0) {
            summary.setLength(summary.length() - 2); // 移除最后的逗号和空格
        }
        
        return summary.toString();
    }
}