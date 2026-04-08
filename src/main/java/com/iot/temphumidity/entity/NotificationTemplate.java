package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 通知模板实体类
 * 管理通知消息模板
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notification_templates")
public class NotificationTemplate extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "template_code", nullable = false, unique = true, length = 64)
    private String templateCode;
    
    @Column(name = "template_name", nullable = false, length = 128)
    private String templateName;
    
    @Column(name = "template_type", nullable = false, length = 32)
    private String templateType;
    
    @Column(name = "template_category", length = 32)
    private String templateCategory;
    
    @Column(name = "template_description", length = 512)
    private String templateDescription;
    
    @Column(name = "trigger_event", length = 64)
    private String triggerEvent;
    
    @Column(name = "trigger_condition", columnDefinition = "JSONB")
    private String triggerCondition;
    
    @Column(name = "subject_template", nullable = false, length = 256)
    private String subjectTemplate;
    
    @Column(name = "content_template", nullable = false, columnDefinition = "TEXT")
    private String contentTemplate;
    
    @Column(name = "content_type", nullable = false, length = 32)
    private String contentType;
    
    @Column(name = "variables_definition", columnDefinition = "JSONB")
    private String variablesDefinition;
    
    @Column(name = "variables_example", columnDefinition = "JSONB")
    private String variablesExample;
    
    @Column(name = "channels", nullable = false, columnDefinition = "JSONB")
    private String channels;
    
    @Column(name = "channel_config", columnDefinition = "JSONB")
    private String channelConfig;
    
    @Column(name = "priority", nullable = false, length = 16)
    private String priority = "normal";
    
    @Column(name = "is_urgent", nullable = false)
    private Boolean isUrgent = false;
    
    @Column(name = "is_silent", nullable = false)
    private Boolean isSilent = false;
    
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 3;
    
    @Column(name = "retry_interval_seconds", nullable = false)
    private Integer retryIntervalSeconds = 60;
    
    @Column(name = "expire_seconds", nullable = false)
    private Integer expireSeconds = 86400;
    
    @Column(name = "rate_limit_per_minute")
    private Integer rateLimitPerMinute;
    
    @Column(name = "rate_limit_per_hour")
    private Integer rateLimitPerHour;
    
    @Column(name = "rate_limit_per_day")
    private Integer rateLimitPerDay;
    
    @Column(name = "throttle_config", columnDefinition = "JSONB")
    private String throttleConfig;
    
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
    
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem = false;
    
    @Column(name = "is_global", nullable = false)
    private Boolean isGlobal = false;
    
    @Column(name = "allowed_users", columnDefinition = "JSONB")
    private String allowedUsers;
    
    @Column(name = "allowed_roles", columnDefinition = "JSONB")
    private String allowedRoles;
    
    @Column(name = "allowed_organizations", columnDefinition = "JSONB")
    private String allowedOrganizations;
    
    @Column(name = "allowed_devices", columnDefinition = "JSONB")
    private String allowedDevices;
    
    @Column(name = "default_recipients", columnDefinition = "JSONB")
    private String defaultRecipients;
    
    @Column(name = "default_cc", columnDefinition = "JSONB")
    private String defaultCc;
    
    @Column(name = "default_bcc", columnDefinition = "JSONB")
    private String defaultBcc;
    
    @Column(name = "sender_name", length = 128)
    private String senderName;
    
    @Column(name = "sender_email", length = 128)
    private String senderEmail;
    
    @Column(name = "sender_phone", length = 32)
    private String senderPhone;
    
    @Column(name = "sender_config", columnDefinition = "JSONB")
    private String senderConfig;
    
    @Column(name = "attachment_config", columnDefinition = "JSONB")
    private String attachmentConfig;
    
    @Column(name = "callback_url", length = 512)
    private String callbackUrl;
    
    @Column(name = "callback_method", length = 16)
    private String callbackMethod;
    
    @Column(name = "callback_headers", columnDefinition = "JSONB")
    private String callbackHeaders;
    
    @Column(name = "audit_config", columnDefinition = "JSONB")
    private String auditConfig;
    
    @Column(name = "localization_config", columnDefinition = "JSONB")
    private String localizationConfig;
    
    @Column(name = "version", nullable = false)
    private Integer version = 1;
    
    @Column(name = "previous_version_id")
    private Long previousVersionId;
    
    @Column(name = "is_latest", nullable = false)
    private Boolean isLatest = true;
    
    @Column(name = "usage_count", nullable = false)
    private Long usageCount = 0L;
    
    @Column(name = "last_used_time")
    private LocalDateTime lastUsedTime;
    
    @Column(name = "last_used_by", length = 64)
    private String lastUsedBy;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "active";
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 模板类型常量
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
    
    // 模板分类常量
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
    
    // 通道常量
    public static final String CHANNEL_EMAIL = "email";
    public static final String CHANNEL_SMS = "sms";
    public static final String CHANNEL_PUSH = "push";
    public static final String CHANNEL_WECHAT = "wechat";
    public static final String CHANNEL_DINGTALK = "dingtalk";
    public static final String CHANNEL_SLACK = "slack";
    public static final String CHANNEL_TELEGRAM = "telegram";
    public static final String CHANNEL_WEBHOOK = "webhook";
    public static final String CHANNEL_MQTT = "mqtt";
    public static final String CHANNEL_IN_APP = "in_app";
    
    // 优先级常量
    public static final String PRIORITY_URGENT = "urgent";
    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_NORMAL = "normal";
    public static final String PRIORITY_LOW = "low";
    
    // 状态常量
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_INACTIVE = "inactive";
    public static final String STATUS_DRAFT = "draft";
    public static final String STATUS_ARCHIVED = "archived";
    public static final String STATUS_DELETED = "deleted";
    
    // 触发事件常量
    public static final String EVENT_ALARM_TRIGGERED = "alarm_triggered";
    public static final String EVENT_ALARM_CLEARED = "alarm_cleared";
    public static final String EVENT_DEVICE_ONLINE = "device_online";
    public static final String EVENT_DEVICE_OFFLINE = "device_offline";
    public static final String EVENT_DEVICE_CONNECTED = "device_connected";
    public static final String EVENT_DEVICE_DISCONNECTED = "device_disconnected";
    public static final String EVENT_SENSOR_ABNORMAL = "sensor_abnormal";
    public static final String EVENT_SENSOR_NORMAL = "sensor_normal";
    public static final String EVENT_DATA_EXCEEDS_THRESHOLD = "data_exceeds_threshold";
    public static final String EVENT_USER_LOGIN = "user_login";
    public static final String EVENT_USER_LOGOUT = "user_logout";
    public static final String EVENT_USER_PASSWORD_CHANGED = "user_password_changed";
    public static final String EVENT_USER_REGISTERED = "user_registered";
    public static final String EVENT_SYSTEM_STARTUP = "system_startup";
    public static final String EVENT_SYSTEM_SHUTDOWN = "system_shutdown";
    public static final String EVENT_SYSTEM_ERROR = "system_error";
    public static final String EVENT_BACKUP_COMPLETED = "backup_completed";
    public static final String EVENT_BACKUP_FAILED = "backup_failed";
    public static final String EVENT_SCHEDULED_TASK_START = "scheduled_task_start";
    public static final String EVENT_SCHEDULED_TASK_COMPLETE = "scheduled_task_complete";
    public static final String EVENT_SCHEDULED_TASK_FAILED = "scheduled_task_failed";
    
    @PrePersist
    protected void onCreate() {
        // super.onCreate(); // BaseEntity可能没有这个方法
        if (templateType == null) {
            templateType = TYPE_INFO;
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
            retryCount = 3;
        }
        if (retryIntervalSeconds == null) {
            retryIntervalSeconds = 60;
        }
        if (expireSeconds == null) {
            expireSeconds = 86400;
        }
        if (isEnabled == null) {
            isEnabled = true;
        }
        if (isSystem == null) {
            isSystem = false;
        }
        if (isGlobal == null) {
            isGlobal = false;
        }
        if (version == null) {
            version = 1;
        }
        if (isLatest == null) {
            isLatest = true;
        }
        if (usageCount == null) {
            usageCount = 0L;
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
    
    /**
     * 判断是否可用
     */
    public boolean isAvailable() {
        return isEnabled && 
               STATUS_ACTIVE.equals(status);
    }
    
    /**
     * 判断是否支持指定通道
     */
    public boolean supportsChannel(String channel) {
        if (channels == null) {
            return false;
        }
        return channels.toLowerCase().contains(channel.toLowerCase());
    }
    
    /**
     * 增加使用次数
     */
    public void incrementUsageCount() {
        this.usageCount = this.usageCount + 1;
        this.lastUsedTime = LocalDateTime.now();
    }
    
    /**
     * 判断是否达到速率限制
     */
    public boolean isRateLimited() {
        // 这里需要根据实际业务逻辑实现
        // 暂时返回false
        return false;
    }
}