package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.enums.AlarmSeverity;
import com.iot.temphumidity.enums.AlarmStatus;
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
 * 报警记录实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alarm_records", indexes = {
    @Index(name = "idx_alarm_record_sensor_id", columnList = "sensorId"),
    @Index(name = "idx_alarm_record_timestamp", columnList = "timestamp"),
    @Index(name = "idx_alarm_record_status", columnList = "status"),
    @Index(name = "idx_alarm_record_processed_by", columnList = "processedBy")
})
@EntityListeners(AuditingEntityListener.class)
public class AlarmRecordEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sensor_id", nullable = false, length = 50)
    private String sensorId;
    
    @Column(name = "device_id", length = 50)
    private String deviceId;
    
    @Column(name = "rule_name", length = 100)
    private String ruleName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 20)
    private AlarmSeverity severity;
    
    @Column(name = "alarm_type", length = 50)
    private String alarmType;
    
    @Column(name = "alarm_value", precision = 10, scale = 2)
    private BigDecimal alarmValue;
    
    @Column(name = "threshold_min", precision = 10, scale = 2)
    private BigDecimal thresholdMin;
    
    @Column(name = "threshold_max", precision = 10, scale = 2)
    private BigDecimal thresholdMax;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AlarmStatus status = AlarmStatus.NEW;
    
    @Column(name = "acknowledged", nullable = false)
    private Boolean acknowledged = false;
    
    @Column(name = "acknowledged_by", length = 50)
    private String acknowledgedBy;
    
    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;
    
    @Column(name = "resolved", nullable = false)
    private Boolean resolved = false;
    
    @Column(name = "resolved_by", length = 50)
    private String resolvedBy;
    
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    
    @Column(name = "processed_by", length = 50)
    private String processedBy;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // 与用户的关联
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User processedByUser;
    
    // 与报警规则的关联
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_rule_id", referencedColumnName = "id")
    private AlarmRuleEntity alarmRule;
    
    // 构造函数
    public AlarmRecordEntity(String sensorId, String ruleName, AlarmSeverity severity, 
                           String alarmType, BigDecimal alarmValue, BigDecimal thresholdMin, 
                           BigDecimal thresholdMax, String description) {
        this.sensorId = sensorId;
        this.ruleName = ruleName;
        this.severity = severity;
        this.alarmType = alarmType;
        this.alarmValue = alarmValue;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.description = description;
        this.status = AlarmStatus.NEW;
        this.timestamp = LocalDateTime.now();
    }
}