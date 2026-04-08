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
 * 数据质量结果实体类
 * 对应PostgreSQL中的data_quality_results表
 */
@Entity
@Table(name = "data_quality_results",
       indexes = {
           @Index(name = "idx_data_quality_results_rule", columnList = "rule_id"),
           @Index(name = "idx_data_quality_results_target", columnList = "target_type, target_id"),
           @Index(name = "idx_data_quality_results_time", columnList = "check_time"),
           @Index(name = "idx_data_quality_results_status", columnList = "check_status")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataQualityResult extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_id", nullable = false)
    private Long ruleId;  // 规则ID
    
    @Column(name = "target_type", nullable = false, length = 32)
    private String targetType;  // 目标类型
    
    @Column(name = "target_id", nullable = false)
    private Long targetId;  // 目标ID
    
    @Column(name = "check_time", nullable = false)
    private LocalDateTime checkTime;  // 检查时间
    
    @Column(name = "check_status", nullable = false, length = 16)
    private String checkStatus;  // 检查状态
    
    @Column(name = "severity", length = 16)
    private String severity;  // 严重程度
    
    @Column(name = "score", precision = 5, scale = 2)
    private Double score;  // 质量分数
    
    @Column(name = "violation_count")
    private Integer violationCount;  // 违规次数
    
    @Column(name = "total_checks")
    private Integer totalChecks;  // 总检查次数
    
    @Column(name = "details", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String details;  // 详细结果 (JSON)
    
    @Column(name = "suggestions", columnDefinition = "TEXT")
    private String suggestions;  // 改进建议
    
    @Column(name = "resolved")
    @Builder.Default
    private Boolean resolved = false;  // 是否已解决
    
    @Column(name = "resolved_by")
    private Long resolvedBy;  // 解决者
    
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;  // 解决时间
    
    @Column(name = "resolved_notes", columnDefinition = "TEXT")
    private String resolvedNotes;  // 解决备注
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // 检查状态枚举
    public enum CheckStatus {
        PASS("pass"),           // 通过
        WARNING("warning"),     // 警告
        FAIL("fail"),           // 失败
        ERROR("error"),         // 错误
        SKIPPED("skipped");     // 跳过
        
        private final String value;
        
        CheckStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 计算质量分数百分比
    public Double getQualityScorePercentage() {
        if (score != null) {
            return score;
        }
        if (totalChecks != null && totalChecks > 0) {
            int passed = totalChecks - (violationCount != null ? violationCount : 0);
            return (double) passed / totalChecks * 100.0;
        }
        return null;
    }
    
    // 获取质量等级
    public String getQualityLevel() {
        Double percentage = getQualityScorePercentage();
        if (percentage == null) return "unknown";
        
        if (percentage >= 95.0) return "excellent";
        if (percentage >= 85.0) return "good";
        if (percentage >= 70.0) return "fair";
        if (percentage >= 60.0) return "poor";
        return "unacceptable";
    }
    
    // 标记为已解决
    public void markAsResolved(Long resolverId, String notes) {
        this.resolved = true;
        this.resolvedBy = resolverId;
        this.resolvedAt = LocalDateTime.now();
        this.resolvedNotes = notes;
    }
    
    // 预持久化回调
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (checkTime == null) {
            checkTime = now;
        }
    }
}