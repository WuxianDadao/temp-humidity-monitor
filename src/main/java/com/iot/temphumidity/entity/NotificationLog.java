package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 通知日志实体类
 * 记录所有发送的通知消息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notification_logs")
public class NotificationLog extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "notification_id", nullable = false, unique = true, length = 64)
    private String notificationId;
    
    @Column(name = "template_code", nullable = false, length = 64)
    private String templateCode;
    
    @Column(name = "template_name", length = 128)
    private String templateName;
    
    @Column(name = "notification_type", nullable = false, length = 32)
    private String notificationType;
    
    @Column(name = "notification_category", length = 32)
    private String notificationCategory;
    
    @Column(name = "trigger_event", length = 64)
    private String triggerEvent;
    
    @Column(name = "trigger_source_type", length = 32)
    private String triggerSourceType;
    
    @Column(name = "trigger_source_id", length = 64)
    private String triggerSourceId;
    
    @Column(name = "trigger_source_name", length = 128)
    private String triggerSourceName;
    
    @Column(name = "trigger_data", columnDefinition = "JSONB")
    private String triggerData;
    
    @Column(name = "subject", nullable = false, length = 256)
    private String subject;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "content_type", nullable = false, length = 32)
    private String contentType;
    
    @Column(name = "variables_used", columnDefinition = "JSONB")
    private String variablesUsed;
    
    @Column(name = "channels", nullable = false, columnDefinition = "JSONB")
    private String channels;
    
    @Column(name = "channel_status", columnDefinition = "JSONB")
    private String channelStatus;
    
    @Column(name = "recipients", nullable = false, columnDefinition = "JSONB")
    private String recipients;
    
    @Column(name = "cc_recipients", columnDefinition = "JSONB")
    private String ccRecipients;
    
    @Column(name = "bcc_recipients", columnDefinition = "JSONB")
    private String bccRecipients;
    
    @Column(name = "sender_name", length = 128)
    private String senderName;
    
    @Column(name = "sender_email", length = 128)
    private String senderEmail;
    
    @Column(name = "sender_phone", length = 32)
    private String senderPhone;
    
    @Column(name = "sender_config", columnDefinition = "JSONB")
    private String senderConfig;
    
    @Column(name = "priority", nullable = false, length = 16)
    private String priority = "normal";
    
    @Column(name = "is_urgent", nullable = false)
    private Boolean isUrgent = false;
    
    @Column(name = "is_silent", nullable = false)
    private Boolean isSilent = false;
    
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;
    
    @Column(name = "max_retry_count", nullable = false)
    private Integer maxRetryCount = 3;
    
    @Column(name = "retry_interval_seconds", nullable = false)
    private Integer retryIntervalSeconds = 60;
    
    @Column(name = "next_retry_time")
    private LocalDateTime nextRetryTime;
    
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    
    @Column(name = "scheduled_send_time")
    private LocalDateTime scheduledSendTime;
    
    @Column(name = "actual_send_time")
    private LocalDateTime actualSendTime;
    
    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;
    
    @Column(name = "read_time")
    private LocalDateTime readTime;
    
    @Column(name = "acknowledge_time")
    private LocalDateTime acknowledgeTime;
    
    @Column(name = "callback_url", length = 512)
    private String callbackUrl;
    
    @Column(name = "callback_method", length = 16)
    private String callbackMethod;
    
    @Column(name = "callback_headers", columnDefinition = "JSONB")
    private String callbackHeaders;
    
    @Column(name = "callback_data", columnDefinition = "JSONB")
    private String callbackData;
    
    @Column(name = "callback_response", columnDefinition = "JSONB")
    private String callbackResponse;
    
    @Column(name = "callback_status_code")
    private Integer callbackStatusCode;
    
    @Column(name = "callback_time")
    private LocalDateTime callbackTime;
    
    @Column(name = "attachments", columnDefinition = "JSONB")
    private String attachments;
    
    @Column(name = "attachment_count", nullable = false)
    private Integer attachmentCount = 0;
    
    @Column(name = "attachment_size_bytes")
    private Long attachmentSizeBytes;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "pending";
    
    @Column(name = "delivery_status", length = 16)
    private String deliveryStatus;
    
    @Column(name = "read_status", nullable = false, length = 16)
    private String readStatus = "unread";
    
    @Column(name = "acknowledge_status", nullable = false, length = 16)
    private String acknowledgeStatus = "unacknowledged";
    
    @Column(name = "error_code", length = 32)
    private String errorCode;
    
    @Column(name = "error_message", length = 1024)
    private String errorMessage;
    
    @Column(name = "error_details", columnDefinition = "JSONB")
    private String errorDetails;
    
    @Column(name = "retry_reason", length = 256)
    private String retryReason;
    
    @Column(name = "cost_estimate")
    private Double costEstimate;
    
    @Column(name = "cost_actual")
    private Double costActual;
    
    @Column(name = "cost_currency", length = 3)
    private String costCurrency = "CNY";
    
    @Column(name = "rate_limit_key", length = 128)
    private String rateLimitKey;
    
    @Column(name = "rate_limit_bucket", length = 64)
    private String rateLimitBucket;
    
    @Column(name = "rate_limit_count")
    private Integer rateLimitCount;
    
    @Column(name = "is_rate_limited", nullable = false)
    private Boolean isRateLimited = false;
    
    @Column(name = "is_duplicate", nullable = false)
    private Boolean isDuplicate = false;
    
    @Column(name = "duplicate_of_id")
    private Long duplicateOfId;
    
    @Column(name = "is_test", nullable = false)
    private Boolean isTest = false;
    
    @Column(name = "is_bulk", nullable = false)
    private Boolean isBulk = false;
    
    @Column(name = "bulk_group_id", length = 64)
    private String bulkGroupId;
    
    @Column(name = "bulk_sequence")
    private Integer bulkSequence;
    
    @Column(name = "bulk_total")
    private Integer bulkTotal;
    
    @Column(name = "user_id", length = 64)
    private String userId;
    
    @Column(name = "user_name", length = 128)
    private String userName;
    
    @Column(name = "user_email", length = 128)
    private String userEmail;
    
    @Column(name = "user_phone", length = 32)
    private String userPhone;
    
    @Column(name = "organization_id", length = 64)
    private String organizationId;
    
    @Column(name = "organization_name", length = 128)
    private String organizationName;
    
    @Column(name = "device_id", length = 64)
    private String deviceId;
    
    @Column(name = "device_name", length = 128)
    private String deviceName;
    
    @Column(name = "sensor_id", length = 64)
    private String sensorId;
    
    @Column(name = "sensor_name", length = 128)
    private String sensorName;
    
    @Column(name = "alarm_id", length = 64)
    private String alarmId;
    
    @Column(name = "alarm_name", length = 128)
    private String alarmName;
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 通知类型常量
    public static final String TYPE_ALARM = "alarm";
    public static final String TYPE_WARNING = "warning";
    public static final String TYPE_INFO = "info";
    public static final String TYPE_SUCCESS = "success";
    public static final String TYPE_ERROR = "error";
    public static final String TYPE_REMINDER = "reminder";
    public static final String TYPE_SYSTEM = "system";
    public static final String TYPE_BUSINESS = "business";
    public static final String TYPE_MARKETING = "marketing";
    public static final String TYPE_VERIFICATION = "verification";
    
    // 通知分类常量
    public static final String CATEGORY_DEVICE = "device";
    public static final String CATEGORY_SENSOR = "sensor";
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_USER = "user";
    public static final String CATEGORY_SYSTEM = "system";
    public static final String CATEGORY_SECURITY = "security";
    public static final String CATEGORY_BUSINESS = "business";
    public static final String CATEGORY_MONITORING = "monitoring";
    
    // 内容类型常量
    public static final String CONTENT_TEXT = "text";
    public static final String CONTENT_HTML = "html";
    public static final String CONTENT_MARKDOWN = "markdown";
    public static final String CONTENT_JSON = "json";
    public static final String CONTENT_XML = "xml";
    
    // 状态常量
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_SENT = "sent";
    public static final String STATUS_DELIVERED = "delivered";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_CANCELLED = "cancelled";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_SCHEDULED = "scheduled";
    public static final String STATUS_RETRYING = "retrying";
    public static final String STATUS_SKIPPED = "skipped";
    public static final String STATUS_DUPLICATE = "duplicate";
    
    // 阅读状态常量
    public static final String READ_STATUS_UNREAD = "unread";
    public static final String READ_STATUS_READ = "read";
    public static final String READ_STATUS_PARTIALLY_READ = "partially_read";
    
    // 投递状态常量
    public static final String DELIVERY_QUEUED = "queued";
    public static final String DELIVERY_SENDING = "sending";
    public static final String DELIVERY_SENT = "sent";
    public static final String DELIVERY_DELIVERED = "delivered";
    public static final String DELIVERY_FAILED = "failed";
    public static final String DELIVERY_BOUNCED = "bounced";
    public static final String DELIVERY_REJECTED = "rejected";
    public static final String DELIVERY_SPAM = "spam";
    public static final String DELIVERY_UNKNOWN = "unknown";
    
    // 阅读状态常量
    public static final String READ_UNREAD = "unread";
    public static final String READ_READ = "read";
    public static final String READ_IGNORED = "ignored";
    public static final String READ_ARCHIVED = "archived";
    public static final String READ_DELETED = "deleted";
    
    // 确认状态常量
    public static final String ACKNOWLEDGE_UNAKNOWLEDGED = "unacknowledged";
    public static final String ACKNOWLEDGE_ACKNOWLEDGED = "acknowledged";
    public static final String ACKNOWLEDGE_IGNORED = "ignored";
    public static final String ACKNOWLEDGE_ESCALATED = "escalated";
    
    // 优先级常量
    public static final String PRIORITY_URGENT = "urgent";
    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_NORMAL = "normal";
    public static final String PRIORITY_LOW = "low";
    
    // 触发源类型常量
    public static final String SOURCE_DEVICE = "device";
    public static final String SOURCE_SENSOR = "sensor";
    public static final String SOURCE_ALARM = "alarm";
    public static final String SOURCE_USER = "user";
    public static final String SOURCE_SYSTEM = "system";
    public static final String SOURCE_SCHEDULER = "scheduler";
    public static final String SOURCE_API = "api";
    public static final String SOURCE_WEBHOOK = "webhook";
    public static final String SOURCE_MANUAL = "manual";
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (notificationType == null) {
            notificationType = TYPE_INFO;
        }
        if (contentType == null) {
            contentType = CONTENT_TEXT;
        }
        if (priority == null) {
            priority = PRIORITY_NORMAL;
        }
        if (isUrgent == null) {
            isUrgent = false;
        }
        if (isSilent == null) {
            isSilent = false;
        }
        if (retryCount == null) {
            retryCount = 0;
        }
        if (maxRetryCount == null) {
            maxRetryCount = 3;
        }
        if (retryIntervalSeconds == null) {
            retryIntervalSeconds = 60;
        }
        if (attachmentCount == null) {
            attachmentCount = 0;
        }
        if (status == null) {
            status = STATUS_PENDING;
        }
        if (readStatus == null) {
            readStatus = READ_UNREAD;
        }
        if (acknowledgeStatus == null) {
            acknowledgeStatus = ACKNOWLEDGE_UNAKNOWLEDGED;
        }
        if (costCurrency == null) {
            costCurrency = "CNY";
        }
        if (isRateLimited == null) {
            isRateLimited = false;
        }
        if (isDuplicate == null) {
            isDuplicate = false;
        }
        if (isTest == null) {
            isTest = false;
        }
        if (isBulk == null) {
            isBulk = false;
        }
        
        // 生成通知ID（如果未提供）
        if (notificationId == null) {
            notificationId = generateNotificationId();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
    }
    
    /**
     * 生成通知ID
     */
    private String generateNotificationId() {
        return "NOTIF_" + System.currentTimeMillis() + "_" + 
               (int)(Math.random() * 10000);
    }
    
    /**
     * 判断是否可重试
     */
    public boolean canRetry() {
        return retryCount < maxRetryCount && 
               (STATUS_FAILED.equals(status) || 
                STATUS_RETRYING.equals(status));
    }
    
    /**
     * 判断是否已过期
     */
    public boolean isExpired() {
        return expireTime != null && LocalDateTime.now().isAfter(expireTime);
    }
    
    /**
     * 判断是否已发送
     */
    public boolean isSent() {
        return STATUS_SENT.equals(status) || 
               STATUS_DELIVERED.equals(status);
    }
    
    /**
     * 判断是否已投递
     */
    public boolean isDelivered() {
        return STATUS_DELIVERED.equals(status);
    }
    
    /**
     * 判断是否已失败
     */
    public boolean isFailed() {
        return STATUS_FAILED.equals(status);
    }
    
    /**
     * 判断是否已取消
     */
    public boolean isCancelled() {
        return STATUS_CANCELLED.equals(status);
    }
    
    /**
     * 判断是否需要立即发送
     */
    public boolean shouldSendNow() {
        if (scheduledSendTime == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(scheduledSendTime) || 
               LocalDateTime.now().isEqual(scheduledSendTime);
    }
    
    /**
     * 准备重试
     */
    public void prepareForRetry() {
        this.retryCount = this.retryCount + 1;
        this.status = STATUS_RETRYING;
        this.nextRetryTime = LocalDateTime.now().plusSeconds(retryIntervalSeconds);
    }
    
    /**
     * 标记为已发送
     */
    public void markAsSent() {
        this.status = STATUS_SENT;
        this.actualSendTime = LocalDateTime.now();
    }
    
    /**
     * 标记为已投递
     */
    public void markAsDelivered() {
        this.status = STATUS_DELIVERED;
        this.deliveryStatus = DELIVERY_DELIVERED;
        this.deliveryTime = LocalDateTime.now();
    }
    
    /**
     * 标记为已失败
     */
    public void markAsFailed(String errorCode, String errorMessage) {
        this.status = STATUS_FAILED;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    /**
     * 标记为已阅读
     */
    public void markAsRead() {
        this.readStatus = READ_STATUS_READ;
        this.readTime = LocalDateTime.now();
    }
}