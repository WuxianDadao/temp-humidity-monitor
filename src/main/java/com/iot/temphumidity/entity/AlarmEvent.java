package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 报警事件实体类
 * 记录发生的报警事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "alarm_events")
public class AlarmEvent extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "alarm_rule_id", nullable = false)
    private Long alarmRuleId;
    
    @Column(name = "device_id", nullable = false)
    private Long deviceId;
    
    @Column(name = "sensor_id")
    private Long sensorId;
    
    @Column(name = "event_type", nullable = false, length = 32)
    private String eventType;
    
    @Column(name = "severity", nullable = false, length = 16)
    private String severity;
    
    @Column(name = "title", nullable = false, length = 256)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "current_value", length = 128)
    private String currentValue;
    
    @Column(name = "threshold_value", length = 128)
    private String thresholdValue;
    
    @Column(name = "unit", length = 32)
    private String unit;
    
    @Column(name = "data_source", length = 64)
    private String dataSource;
    
    @Column(name = "data_timestamp")
    private LocalDateTime dataTimestamp;
    
    @Column(name = "occurrence_time", nullable = false)
    private LocalDateTime occurrenceTime = LocalDateTime.now();
    
    @Column(name = "acknowledged", nullable = false)
    private Boolean acknowledged = false;
    
    @Column(name = "acknowledged_by", length = 64)
    private String acknowledgedBy;
    
    @Column(name = "acknowledged_time")
    private LocalDateTime acknowledgedTime;
    
    @Column(name = "acknowledged_note", length = 512)
    private String acknowledgedNote;
    
    @Column(name = "resolved", nullable = false)
    private Boolean resolved = false;
    
    @Column(name = "resolved_by", length = 64)
    private String resolvedBy;
    
    @Column(name = "resolved_time")
    private LocalDateTime resolvedTime;
    
    @Column(name = "resolved_note", length = 512)
    private String resolvedNote;
    
    @Column(name = "duration_seconds")
    private Long durationSeconds;
    
    @Column(name = "notification_count", nullable = false)
    private Integer notificationCount = 0;
    
    @Column(name = "last_notification_time")
    private LocalDateTime lastNotificationTime;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "active";
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 事件类型常量
    public static final String EVENT_TYPE_TEMPERATURE_HIGH = "temperature_high";
    public static final String EVENT_TYPE_TEMPERATURE_LOW = "temperature_low";
    public static final String EVENT_TYPE_HUMIDITY_HIGH = "humidity_high";
    public static final String EVENT_TYPE_HUMIDITY_LOW = "humidity_low";
    public static final String EVENT_TYPE_DEVICE_OFFLINE = "device_offline";
    public static final String EVENT_TYPE_DEVICE_ONLINE = "device_online";
    public static final String EVENT_TYPE_BATTERY_LOW = "battery_low";
    public static final String EVENT_TYPE_SIGNAL_WEAK = "signal_weak";
    public static final String EVENT_TYPE_CUSTOM = "custom";
    
    // 严重程度常量
    public static final String SEVERITY_CRITICAL = "critical";
    public static final String SEVERITY_HIGH = "high";
    public static final String SEVERITY_MEDIUM = "medium";
    public static final String SEVERITY_LOW = "low";
    public static final String SEVERITY_INFO = "info";
    
    // 状态常量
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_ACKNOWLEDGED = "acknowledged";
    public static final String STATUS_RESOLVED = "resolved";
    public static final String STATUS_CLOSED = "closed";
    public static final String STATUS_EXPIRED = "expired";
    
    // 数据源常量
    public static final String DATA_SOURCE_REAL_TIME = "real_time";
    public static final String DATA_SOURCE_HISTORY = "history";
    public static final String DATA_SOURCE_MANUAL = "manual";
    public static final String DATA_SOURCE_TEST = "test";
    
    @PrePersist
    protected void onCreate() {
        // super.onCreate(); // BaseEntity可能没有这个方法
        if (occurrenceTime == null) {
            occurrenceTime = LocalDateTime.now();
        }
        if (acknowledged == null) {
            acknowledged = false;
        }
        if (resolved == null) {
            resolved = false;
        }
        if (notificationCount == null) {
            notificationCount = 0;
        }
        if (status == null) {
            status = STATUS_ACTIVE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        // super.onUpdate(); // BaseEntity可能没有这个方法
        this.updatedAt = LocalDateTime.now();
    }
}