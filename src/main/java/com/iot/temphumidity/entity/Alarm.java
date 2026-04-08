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
 * 报警记录实体类
 * 对应PostgreSQL中的alarms表
 */
@Entity
@Table(name = "alarms", 
       indexes = {
           @Index(name = "idx_alarms_rule_id", columnList = "alarm_rule_id"),
           @Index(name = "idx_alarms_device_tag_id", columnList = "device_tag_id"),
           @Index(name = "idx_alarms_status", columnList = "status"),
           @Index(name = "idx_alarms_severity", columnList = "severity"),
           @Index(name = "idx_alarms_trigger_time", columnList = "trigger_time"),
           @Index(name = "idx_alarms_device_id_trigger_time", columnList = "device_id, trigger_time DESC")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_rule_id", foreignKey = @ForeignKey(name = "fk_alarms_alarm_rule_id"))
    private AlarmRule alarmRule;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_tag_id", foreignKey = @ForeignKey(name = "fk_alarms_device_tag_id"))
    private DeviceTag deviceTag;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "fk_alarms_device_id"))
    private Device device;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_tag_id", foreignKey = @ForeignKey(name = "fk_alarms_sensor_tag_id"))
    private SensorTag sensorTag;
    
    @Column(name = "actual_value", precision = 10, scale = 2)
    private Double actualValue;
    
    @Column(name = "threshold_value", precision = 10, scale = 2)
    private Double thresholdValue;
    
    @Column(name = "severity", length = 32)
    private String severity;
    
    @Column(name = "status", length = 32)
    private String status;
    
    @Column(name = "title", length = 256)
    private String title;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "trigger_time")
    private LocalDateTime triggerTime;
    
    @Column(name = "acknowledged_time")
    private LocalDateTime acknowledgedTime;
    
    @Column(name = "resolved_time")
    private LocalDateTime resolvedTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acknowledged_by", foreignKey = @ForeignKey(name = "fk_alarms_acknowledged_by"))
    private User acknowledgedBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by", foreignKey = @ForeignKey(name = "fk_alarms_resolved_by"))
    private User resolvedBy;
    
    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;
    
    @Column(name = "notification_sent")
    private boolean notificationSent;
    
    @Column(name = "notification_sent_time")
    private LocalDateTime notificationSentTime;
    
    @Column(name = "retry_count")
    private Integer retryCount;
    
    @Column(name = "last_retry_time")
    private LocalDateTime lastRetryTime;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 常量定义
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_RESOLVED = "RESOLVED";
    public static final String STATUS_IGNORED = "IGNORED";
    
    public static final String SEVERITY_INFO = "INFO";
    public static final String SEVERITY_WARNING = "WARNING";
    public static final String SEVERITY_ERROR = "ERROR";
    public static final String SEVERITY_CRITICAL = "CRITICAL";
    
    /**
     * 创建新报警记录
     */
    public static Alarm createNewAlarm(AlarmRule alarmRule, Device device, DeviceTag deviceTag, 
                                     SensorTag sensorTag, Double actualValue, Double thresholdValue,
                                     String severity, String title, String message) {
        return Alarm.builder()
                .alarmRule(alarmRule)
                .device(device)
                .deviceTag(deviceTag)
                .sensorTag(sensorTag)
                .actualValue(actualValue)
                .thresholdValue(thresholdValue)
                .severity(severity)
                .status(STATUS_PENDING)
                .title(title)
                .message(message)
                .triggerTime(LocalDateTime.now())
                .notificationSent(false)
                .retryCount(0)
                .build();
    }
    
    /**
     * 确认报警
     */
    public void acknowledge(User acknowledgedBy) {
        if (STATUS_PENDING.equals(this.status)) {
            this.status = STATUS_PROCESSING;
            this.acknowledgedBy = acknowledgedBy;
            this.acknowledgedTime = LocalDateTime.now();
        }
    }
    
    /**
     * 解决报警
     */
    public void resolve(User resolvedBy, String resolutionNotes) {
        this.status = STATUS_RESOLVED;
        this.resolvedBy = resolvedBy;
        this.resolutionNotes = resolutionNotes;
        this.resolvedTime = LocalDateTime.now();
    }
    
    /**
     * 忽略报警
     */
    public void ignore(User ignoredBy) {
        this.status = STATUS_IGNORED;
        this.resolvedBy = ignoredBy;
        this.resolutionNotes = "手动忽略";
        this.resolvedTime = LocalDateTime.now();
    }
    
    /**
     * 标记通知已发送
     */
    public void markNotificationSent() {
        this.notificationSent = true;
        this.notificationSentTime = LocalDateTime.now();
    }
    
    /**
     * 增加重试计数
     */
    public void incrementRetryCount() {
        if (this.retryCount == null) {
            this.retryCount = 0;
        }
        this.retryCount++;
        this.lastRetryTime = LocalDateTime.now();
    }
    
    /**
     * 获取报警持续时间（分钟）
     */
    public Long getDurationMinutes() {
        if (triggerTime == null) {
            return 0L;
        }
        
        LocalDateTime endTime = resolvedTime != null ? resolvedTime : LocalDateTime.now();
        return java.time.Duration.between(triggerTime, endTime).toMinutes();
    }
    
    /**
     * 检查是否未处理
     */
    public boolean isPending() {
        return STATUS_PENDING.equals(this.status);
    }
    
    /**
     * 检查是否已解决
     */
    public boolean isResolved() {
        return STATUS_RESOLVED.equals(this.status);
    }
    
    /**
     * 检查是否已忽略
     */
    public boolean isIgnored() {
        return STATUS_IGNORED.equals(this.status);
    }
    
    /**
     * 检查是否需要重试通知
     */
    public boolean needsNotificationRetry() {
        return !notificationSent && (retryCount == null || retryCount < 3);
    }
}