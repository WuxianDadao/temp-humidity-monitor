package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * 增强报警规则实体类
 * 对应PostgreSQL中的alarm_rules_enhanced表
 */
@Entity
@Table(name = "alarm_rules_enhanced",
       indexes = {
           @Index(name = "idx_alarm_rules_enhanced_rule", columnList = "alarm_rule_id"),
           @Index(name = "idx_alarm_rules_enhanced_type", columnList = "enhancement_type"),
           @Index(name = "idx_alarm_rules_enhanced_priority", columnList = "priority"),
           @Index(name = "idx_alarm_rules_enhanced_status", columnList = "status")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AlarmRuleEnhanced extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "alarm_rule_id", nullable = false)
    private Long alarmRuleId;  // 报警规则ID
    
    @Column(name = "enhancement_type", nullable = false, length = 32)
    private String enhancementType;  // 增强类型
    
    @Column(name = "priority")
    @Builder.Default
    private Integer priority = 0;  // 优先级
    
    @Column(name = "escalation_level")
    @Builder.Default
    private Integer escalationLevel = 1;  // 升级级别
    
    @Column(name = "escalation_delay_minutes")
    private Integer escalationDelayMinutes;  // 升级延迟分钟
    
    @Column(name = "recovery_check_enabled")
    @Builder.Default
    private Boolean recoveryCheckEnabled = true;  // 恢复检查是否启用
    
    @Column(name = "recovery_duration_minutes")
    private Integer recoveryDurationMinutes;  // 恢复持续时间分钟
    
    @Column(name = "auto_acknowledge")
    @Builder.Default
    private Boolean autoAcknowledge = false;  // 是否自动确认
    
    @Column(name = "auto_acknowledge_delay_minutes")
    private Integer autoAcknowledgeDelayMinutes;  // 自动确认延迟分钟
    
    @Column(name = "suppression_enabled")
    @Builder.Default
    private Boolean suppressionEnabled = false;  // 抑制是否启用
    
    @Column(name = "suppression_window_minutes")
    private Integer suppressionWindowMinutes;  // 抑制窗口分钟
    
    @Column(name = "suppression_max_count")
    private Integer suppressionMaxCount;  // 抑制最大次数
    
    @Column(name = "correlation_enabled")
    @Builder.Default
    private Boolean correlationEnabled = false;  // 关联分析是否启用
    
    @Column(name = "correlation_rules", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String correlationRules;  // 关联规则 (JSON)
    
    @Column(name = "notification_template", columnDefinition = "TEXT")
    private String notificationTemplate;  // 通知模板
    
    @Column(name = "advanced_conditions", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String advancedConditions;  // 高级条件 (JSON)
    
    @Column(name = "action_script", columnDefinition = "TEXT")
    private String actionScript;  // 动作脚本
    
    @Column(name = "status", length = 16)
    @Builder.Default
    private String status = "active";  // 状态
    
    @Column(name = "enabled")
    @Builder.Default
    private Boolean enabled = true;  // 是否启用
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;  // 备注
    
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;  // 最后修改者
    
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;  // 最后修改时间
    
    @Column(name = "created_by")
    private Long createdBy;  // 创建者
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 增强类型枚举
    public enum EnhancementType {
        ESCALATION("escalation"),          // 升级增强
        RECOVERY("recovery"),              // 恢复增强
        NOTIFICATION("notification"),      // 通知增强
        CORRELATION("correlation"),        // 关联增强
        SUPPRESSION("suppression"),        // 抑制增强
        ADVANCED("advanced");              // 高级增强
        
        private final String value;
        
        EnhancementType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 状态枚举
    public enum Status {
        ACTIVE("active"),
        INACTIVE("inactive"),
        TESTING("testing"),
        ARCHIVED("archived");
        
        private final String value;
        
        Status(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 检查是否需要升级
    public boolean requiresEscalation() {
        return escalationLevel != null && escalationLevel > 1;
    }
    
    // 检查是否支持自动确认
    public boolean supportsAutoAcknowledge() {
        return Boolean.TRUE.equals(autoAcknowledge) && autoAcknowledgeDelayMinutes != null && autoAcknowledgeDelayMinutes > 0;
    }
    
    // 检查是否支持抑制
    public boolean supportsSuppression() {
        return Boolean.TRUE.equals(suppressionEnabled) && 
               suppressionWindowMinutes != null && suppressionWindowMinutes > 0 &&
               suppressionMaxCount != null && suppressionMaxCount > 0;
    }
    
    // 检查是否支持关联分析
    public boolean supportsCorrelation() {
        return Boolean.TRUE.equals(correlationEnabled) && correlationRules != null && !correlationRules.isEmpty();
    }
    
    // 检查是否支持恢复检查
    public boolean supportsRecoveryCheck() {
        return Boolean.TRUE.equals(recoveryCheckEnabled) && recoveryDurationMinutes != null && recoveryDurationMinutes > 0;
    }
    
    // 获取升级延迟（秒）
    public Integer getEscalationDelaySeconds() {
        return escalationDelayMinutes != null ? escalationDelayMinutes * 60 : null;
    }
    
    // 获取恢复持续时间（秒）
    public Integer getRecoveryDurationSeconds() {
        return recoveryDurationMinutes != null ? recoveryDurationMinutes * 60 : null;
    }
    
    // 获取抑制窗口（秒）
    public Integer getSuppressionWindowSeconds() {
        return suppressionWindowMinutes != null ? suppressionWindowMinutes * 60 : null;
    }
    
    // 更新最后修改信息
    public void updateLastModified(Long userId) {
        this.lastModifiedBy = userId;
        this.lastModifiedAt = LocalDateTime.now();
    }
    
    // 预持久化回调
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }
    
    // 预更新回调
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}