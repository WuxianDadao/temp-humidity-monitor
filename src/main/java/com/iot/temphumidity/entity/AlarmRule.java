package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 报警规则实体类
 * 对应PostgreSQL中的alarm_rules表
 */
@Entity
@Table(name = "alarm_rules", 
       indexes = {
           @Index(name = "idx_alarm_rules_device_tag_id", columnList = "device_tag_id"),
           @Index(name = "idx_alarm_rules_enabled", columnList = "enabled"),
           @Index(name = "idx_alarm_rules_severity", columnList = "severity")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "rule_name", nullable = false, length = 128)
    private String ruleName;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_tag_id", foreignKey = @ForeignKey(name = "fk_alarm_rules_device_tag_id"))
    private DeviceTag deviceTag;
    
    @Column(name = "condition_type", length = 32)
    private String conditionType;
    
    @Column(name = "condition_expression", columnDefinition = "TEXT")
    private String conditionExpression;
    
    @Column(name = "threshold_min_temp")
    private Double thresholdMinTemp;
    
    @Column(name = "threshold_max_temp")
    private Double thresholdMaxTemp;
    
    @Column(name = "threshold_min_humidity")
    private Double thresholdMinHumidity;
    
    @Column(name = "threshold_max_humidity")
    private Double thresholdMaxHumidity;
    
    @Column(name = "duration_seconds")
    private Integer durationSeconds;
    
    @Column(name = "cooldown_seconds")
    @Builder.Default
    private Integer cooldownSeconds = 300;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 16)
    @Builder.Default
    private AlarmSeverity severity = AlarmSeverity.WARNING;
    
    @Column(name = "enabled")
    @Builder.Default
    private Boolean enabled = true;
    
    @Column(name = "notification_channels", length = 255)
    private String notificationChannels;
    
    @Column(name = "notification_template", columnDefinition = "TEXT")
    private String notificationTemplate;
    
    @Column(name = "escalation_policy", columnDefinition = "JSONB")
    private String escalationPolicy;
    
    @Column(name = "auto_acknowledge")
    @Builder.Default
    private Boolean autoAcknowledge = false;
    
    @Column(name = "auto_resolve")
    @Builder.Default
    private Boolean autoResolve = false;
    
    @Column(name = "tags", length = 255)
    private String tags;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    /**
     * 报警严重性枚举
     */
    public enum AlarmSeverity {
        INFO("info"),
        WARNING("warning"),
        MINOR("minor"),
        MAJOR("major"),
        CRITICAL("critical");
        
        private final String value;
        
        AlarmSeverity(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static AlarmSeverity fromValue(String value) {
            for (AlarmSeverity severity : AlarmSeverity.values()) {
                if (severity.value.equalsIgnoreCase(value)) {
                    return severity;
                }
            }
            throw new IllegalArgumentException("未知的报警严重性: " + value);
        }
    }
    
    /**
     * 检查是否应该触发报警
     */
    public boolean shouldTrigger(Double temperature, Double humidity) {
        if (!enabled) return false;
        
        boolean tempTriggered = false;
        boolean humidityTriggered = false;
        
        if (temperature != null) {
            if (thresholdMinTemp != null && temperature < thresholdMinTemp) {
                tempTriggered = true;
            }
            if (thresholdMaxTemp != null && temperature > thresholdMaxTemp) {
                tempTriggered = true;
            }
        }
        
        if (humidity != null) {
            if (thresholdMinHumidity != null && humidity < thresholdMinHumidity) {
                humidityTriggered = true;
            }
            if (thresholdMaxHumidity != null && humidity > thresholdMaxHumidity) {
                humidityTriggered = true;
            }
        }
        
        return tempTriggered || humidityTriggered;
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
    
    /**
     * 获取标签列表
     */
    public String[] getTagList() {
        if (tags == null || tags.trim().isEmpty()) {
            return new String[0];
        }
        return tags.split(",");
    }
}