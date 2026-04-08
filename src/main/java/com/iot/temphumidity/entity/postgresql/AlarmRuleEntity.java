package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.enums.AlarmSeverity;
import com.iot.temphumidity.enums.StatisticsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 报警规则实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alarm_rules", indexes = {
    @Index(name = "idx_alarm_rule_sensor_id", columnList = "sensorId"),
    @Index(name = "idx_alarm_rule_status", columnList = "status"),
    @Index(name = "idx_alarm_rule_created_by", columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
public class AlarmRuleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_name", nullable = false, length = 100)
    private String ruleName;
    
    @Column(name = "sensor_id", length = 50)
    private String sensorId;
    
    @Column(name = "device_id", length = 50)
    private String deviceId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statistics_type", length = 20)
    private StatisticsType statisticsType;
    
    @Column(name = "threshold_min", precision = 10, scale = 2)
    private BigDecimal thresholdMin;
    
    @Column(name = "threshold_max", precision = 10, scale = 2)
    private BigDecimal thresholdMax;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 20)
    private AlarmSeverity severity;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
    
    @Column(name = "status", length = 20)
    private String status = "active";
    
    @Column(name = "created_by", length = 50)
    private String createdBy;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // 与用户的关联
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User createdByUser;
    
    // 构造函数
    public AlarmRuleEntity(String ruleName, String sensorId, StatisticsType statisticsType, 
                          BigDecimal thresholdMin, BigDecimal thresholdMax, AlarmSeverity severity) {
        this.ruleName = ruleName;
        this.sensorId = sensorId;
        this.statisticsType = statisticsType;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.severity = severity;
        this.enabled = true;
        this.status = "active";
    }
}