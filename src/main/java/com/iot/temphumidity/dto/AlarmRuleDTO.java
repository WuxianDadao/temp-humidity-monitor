package com.iot.temphumidity.dto;

import com.iot.temphumidity.enums.RuleType;
import com.iot.temphumidity.enums.TargetType;
import com.iot.temphumidity.entity.AlarmRule;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 报警规则数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRuleDTO {
    
    /**
     * 规则ID
     */
    private Long id;
    
    /**
     * 规则名称
     */
    private String ruleName;
    
    /**
     * 规则描述
     */
    private String description;
    
    /**
     * 设备标签ID
     */
    private Long deviceTagId;
    
    /**
     * 设备标签名称
     */
    private String deviceTagName;
    
    /**
     * 规则类型
     */
    private RuleType ruleType;
    
    /**
     * 条件类型
     */
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
    private String tags;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 创建人ID
     */
    private Long createdBy;
    
    /**
     * 创建人姓名
     */
    private String createdByName;
    
    /**
     * 目标类型
     */
    private TargetType targetType;
    
    /**
     * 目标ID
     */
    private Long targetId;
    
    /**
     * 目标名称
     */
    private String targetName;
    
    /**
     * 统计信息 - 触发次数
     */
    private Integer triggerCount;
    
    /**
     * 统计信息 - 未处理报警数
     */
    private Integer pendingAlarmCount;
    
    /**
     * 统计信息 - 最后触发时间
     */
    private LocalDateTime lastTriggeredAt;
    
    /**
     * 从实体转换
     */
    public static AlarmRuleDTO fromEntity(AlarmRule entity) {
        if (entity == null) {
            return null;
        }
        
        return AlarmRuleDTO.builder()
                .id(entity.getId())
                .ruleName(entity.getRuleName())
                .description(entity.getDescription())
                .deviceTagId(entity.getDeviceTag() != null ? entity.getDeviceTag().getId() : null)
                .conditionType(entity.getConditionType())
                .conditionExpression(entity.getConditionExpression())
                .thresholdMinTemp(entity.getThresholdMinTemp())
                .thresholdMaxTemp(entity.getThresholdMaxTemp())
                .thresholdMinHumidity(entity.getThresholdMinHumidity())
                .thresholdMaxHumidity(entity.getThresholdMaxHumidity())
                .durationSeconds(entity.getDurationSeconds())
                .cooldownSeconds(entity.getCooldownSeconds())
                .severity(entity.getSeverity())
                .enabled(entity.getEnabled())
                .notificationChannels(entity.getNotificationChannels())
                .notificationTemplate(entity.getNotificationTemplate())
                .escalationPolicy(entity.getEscalationPolicy())
                .autoAcknowledge(entity.getAutoAcknowledge())
                .autoResolve(entity.getAutoResolve())
                .tags(entity.getTags())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }
    
    /**
     * 转换为实体（用于创建/更新）
     */
    public AlarmRule toEntity() {
        AlarmRule entity = AlarmRule.builder()
                .id(this.id)
                .ruleName(this.ruleName)
                .description(this.description)
                .conditionType(this.conditionType)
                .conditionExpression(this.conditionExpression)
                .thresholdMinTemp(this.thresholdMinTemp)
                .thresholdMaxTemp(this.thresholdMaxTemp)
                .thresholdMinHumidity(this.thresholdMinHumidity)
                .thresholdMaxHumidity(this.thresholdMaxHumidity)
                .durationSeconds(this.durationSeconds)
                .cooldownSeconds(this.cooldownSeconds != null ? this.cooldownSeconds : 300)
                .severity(this.severity != null ? this.severity : AlarmRule.AlarmSeverity.WARNING)
                .enabled(this.enabled != null ? this.enabled : true)
                .notificationChannels(this.notificationChannels)
                .notificationTemplate(this.notificationTemplate)
                .escalationPolicy(this.escalationPolicy)
                .autoAcknowledge(this.autoAcknowledge != null ? this.autoAcknowledge : false)
                .autoResolve(this.autoResolve != null ? this.autoResolve : false)
                .tags(this.tags)
                .createdBy(this.createdBy)
                .build();
        
        return entity;
    }
    
    /**
     * 获取显示名称
     */
    public String getDisplayName() {
        if (deviceTagName != null && !deviceTagName.isEmpty()) {
            return ruleName + " (" + deviceTagName + ")";
        }
        return ruleName;
    }
    
    /**
     * 检查是否包含温度阈值
     */
    public boolean hasTemperatureThreshold() {
        return thresholdMinTemp != null || thresholdMaxTemp != null;
    }
    
    /**
     * 检查是否包含湿度阈值
     */
    public boolean hasHumidityThreshold() {
        return thresholdMinHumidity != null || thresholdMaxHumidity != null;
    }
    
    /**
     * 获取通知渠道列表
     */
    public String[] getNotificationChannelList() {
        if (notificationChannels == null || notificationChannels.trim().isEmpty()) {
            return new String[0];
        }
        return notificationChannels.split(",");
    }
}