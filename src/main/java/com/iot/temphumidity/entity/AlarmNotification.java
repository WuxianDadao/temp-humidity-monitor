package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 报警通知实体类
 * 记录报警通知发送记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "alarm_notifications")
public class AlarmNotification extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "alarm_event_id", nullable = false)
    private Long alarmEventId;
    
    @Column(name = "notification_type", nullable = false, length = 32)
    private String notificationType;
    
    @Column(name = "notification_channel", nullable = false, length = 32)
    private String notificationChannel;
    
    @Column(name = "receiver_type", nullable = false, length = 32)
    private String receiverType;
    
    @Column(name = "receiver_id")
    private Long receiverId;
    
    @Column(name = "receiver_address", length = 256)
    private String receiverAddress;
    
    @Column(name = "title", nullable = false, length = 256)
    private String title;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "template_id")
    private Long templateId;
    
    @Column(name = "template_name", length = 128)
    private String templateName;
    
    @Column(name = "params", columnDefinition = "JSONB")
    private String params;
    
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    
    @Column(name = "sent_time")
    private LocalDateTime sentTime;
    
    @Column(name = "delivered_time")
    private LocalDateTime deliveredTime;
    
    @Column(name = "read_time")
    private LocalDateTime readTime;
    
    @Column(name = "acknowledged_time")
    private LocalDateTime acknowledgedTime;
    
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;
    
    @Column(name = "max_retries", nullable = false)
    private Integer maxRetries = 3;
    
    @Column(name = "next_retry_time")
    private LocalDateTime nextRetryTime;
    
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    
    @Column(name = "error_stack", columnDefinition = "TEXT")
    private String errorStack;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "pending";
    
    @Column(name = "delivery_status", length = 16)
    private String deliveryStatus;
    
    @Column(name = "priority", nullable = false, length = 16)
    private String priority = "normal";
    
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 通知类型常量
    public static final String TYPE_ALARM_TRIGGER = "alarm_trigger";
    public static final String TYPE_ALARM_ACK = "alarm_ack";
    public static final String TYPE_ALARM_RESOLVE = "alarm_resolve";
    public static final String TYPE_ALARM_ESCALATION = "alarm_escalation";
    public static final String TYPE_ALARM_TEST = "alarm_test";
    public static final String TYPE_ALARM_SUMMARY = "alarm_summary";
    public static final String TYPE_DEVICE_STATUS = "device_status";
    public static final String TYPE_SYSTEM_NOTICE = "system_notice";
    
    // 通知渠道常量
    public static final String CHANNEL_SMS = "sms";
    public static final String CHANNEL_EMAIL = "email";
    public static final String CHANNEL_WECHAT = "wechat";
    public static final String CHANNEL_DINGTALK = "dingtalk";
    public static final String CHANNEL_FEISHU = "feishu";
    public static final String CHANNEL_WEBHOOK = "webhook";
    public static final String CHANNEL_IN_APP = "in_app";
    public static final String CHANNEL_PUSH = "push";
    
    // 接收者类型常量
    public static final String RECEIVER_TYPE_USER = "user";
    public static final String RECEIVER_TYPE_ROLE = "role";
    public static final String RECEIVER_TYPE_GROUP = "group";
    public static final String RECEIVER_TYPE_DEVICE = "device";
    public static final String RECEIVER_TYPE_SYSTEM = "system";
    
    // 状态常量
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_SENDING = "sending";
    public static final String STATUS_SENT = "sent";
    public static final String STATUS_DELIVERED = "delivered";
    public static final String STATUS_READ = "read";
    public static final String STATUS_ACKNOWLEDGED = "acknowledged";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_RETRYING = "retrying";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_CANCELLED = "cancelled";
    
    // 投递状态常量
    public static final String DELIVERY_SUCCESS = "success";
    public static final String DELIVERY_FAILED = "failed";
    public static final String DELIVERY_TIMEOUT = "timeout";
    public static final String DELIVERY_REJECTED = "rejected";
    public static final String DELIVERY_UNDELIVERABLE = "undeliverable";
    
    // 优先级常量
    public static final String PRIORITY_URGENT = "urgent";
    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_NORMAL = "normal";
    public static final String PRIORITY_LOW = "low";
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (retryCount == null) {
            retryCount = 0;
        }
        if (maxRetries == null) {
            maxRetries = 3;
        }
        if (status == null) {
            status = STATUS_PENDING;
        }
        if (priority == null) {
            priority = PRIORITY_NORMAL;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
    }
    
    /**
     * 判断是否需要重试
     */
    public boolean shouldRetry() {
        return STATUS_FAILED.equals(status) && 
               retryCount < maxRetries && 
               (nextRetryTime == null || LocalDateTime.now().isAfter(nextRetryTime));
    }
    
    /**
     * 判断是否已过期
     */
    public boolean isExpired() {
        return expireTime != null && LocalDateTime.now().isAfter(expireTime);
    }
}