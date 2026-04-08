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
 * 数据质量规则实体类
 * 对应PostgreSQL中的data_quality_rules表
 */
@Entity
@Table(name = "data_quality_rules",
       indexes = {
           @Index(name = "idx_data_quality_rules_target", columnList = "target_type, target_id"),
           @Index(name = "idx_data_quality_rules_status", columnList = "status"),
           @Index(name = "idx_data_quality_rules_type", columnList = "rule_type")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataQualityRule extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_name", nullable = false, length = 128)
    private String ruleName;  // 规则名称
    
    @Column(name = "rule_type", nullable = false, length = 32)
    private String ruleType;  // 规则类型
    
    @Column(name = "target_type", nullable = false, length = 32)
    private String targetType;  // 目标类型 (sensor, device, gateway)
    
    @Column(name = "target_id")
    private Long targetId;  // 目标ID
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // 规则描述
    
    @Column(name = "condition_expression", columnDefinition = "TEXT")
    private String conditionExpression;  // 条件表达式
    
    @Column(name = "check_frequency", length = 16)
    private String checkFrequency;  // 检查频率
    
    @Column(name = "severity", length = 16)
    @Builder.Default
    private String severity = "warning";  // 严重程度
    
    @Column(name = "enabled")
    @Builder.Default
    private Boolean enabled = true;  // 是否启用
    
    @Column(name = "status", length = 16)
    @Builder.Default
    private String status = "active";  // 状态 (active, inactive, testing)
    
    @Column(name = "parameters", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String parameters;  // 规则参数 (JSON)
    
    @Column(name = "created_by")
    private Long createdBy;  // 创建者
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 规则类型枚举
    public enum RuleType {
        RANGE_CHECK("range_check"),  // 范围检查
        NULL_CHECK("null_check"),     // 空值检查
        STALENESS_CHECK("staleness_check"),  // 陈旧性检查
        RATE_OF_CHANGE("rate_of_change"),  // 变化率检查
        STATISTICAL("statistical"),   // 统计检查
        CUSTOM("custom");             // 自定义规则
        
        private final String value;
        
        RuleType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 目标类型枚举
    public enum TargetType {
        SENSOR("sensor"),
        DEVICE("device"),
        GATEWAY("gateway"),
        SYSTEM("system");
        
        private final String value;
        
        TargetType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 严重程度枚举
    public enum Severity {
        INFO("info"),
        WARNING("warning"),
        ERROR("error"),
        CRITICAL("critical");
        
        private final String value;
        
        Severity(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 检查频率枚举
    public enum CheckFrequency {
        REALTIME("realtime"),  // 实时
        MINUTELY("minutely"),  // 每分钟
        HOURLY("hourly"),      // 每小时
        DAILY("daily"),        // 每天
        WEEKLY("weekly"),      // 每周
        MONTHLY("monthly");    // 每月
        
        private final String value;
        
        CheckFrequency(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
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