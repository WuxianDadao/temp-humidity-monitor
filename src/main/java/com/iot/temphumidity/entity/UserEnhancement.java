package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户增强信息实体类
 * 对应PostgreSQL中的user_enhancements表
 * 存储用户的扩展信息和偏好设置
 */
@Entity
@Table(name = "user_enhancements",
       indexes = {
           @Index(name = "idx_user_enhancements_user_id", columnList = "user_id")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEnhancement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "bio", length = 500)
    private String bio; // 个人简介
    
    @Column(name = "location", length = 128)
    private String location; // 位置
    
    @Column(name = "website", length = 255)
    private String website; // 个人网站
    
    @Column(name = "birth_date")
    private LocalDate birthDate; // 出生日期
    
    @Column(name = "gender", length = 16)
    private String gender; // 性别
    
    @Column(name = "preferred_notification_channels", length = 128)
    private String preferredNotificationChannels; // 偏好通知渠道
    
    @Column(name = "notification_frequency", length = 16)
    private String notificationFrequency; // 通知频率
    
    @Column(name = "theme", length = 32)
    @Builder.Default
    private String theme = "light"; // 主题偏好
    
    @Column(name = "dashboard_layout", length = 32)
    @Builder.Default
    private String dashboardLayout = "default"; // 仪表板布局
    
    @Column(name = "time_format", length = 16)
    @Builder.Default
    private String timeFormat = "24h"; // 时间格式
    
    @Column(name = "date_format", length = 32)
    @Builder.Default
    private String dateFormat = "yyyy-MM-dd"; // 日期格式
    
    @Column(name = "number_format", length = 16)
    @Builder.Default
    private String numberFormat = "comma"; // 数字格式
    
    @Column(name = "temperature_unit", length = 8)
    @Builder.Default
    private String temperatureUnit = "celsius"; // 温度单位
    
    @Column(name = "humidity_unit", length = 8)
    @Builder.Default
    private String humidityUnit = "percentage"; // 湿度单位
    
    @Column(name = "pressure_unit", length = 8)
    @Builder.Default
    private String pressureUnit = "hpa"; // 压力单位
    
    @Column(name = "preferred_chart_type", length = 32)
    @Builder.Default
    private String preferredChartType = "line"; // 偏好图表类型
    
    @Column(name = "refresh_interval")
    @Builder.Default
    private Integer refreshInterval = 30; // 刷新间隔(秒)
    
    @Column(name = "auto_refresh_enabled")
    @Builder.Default
    private Boolean autoRefreshEnabled = true; // 自动刷新启用
    
    @Column(name = "export_format", length = 16)
    @Builder.Default
    private String exportFormat = "csv"; // 导出格式
    
    @Column(name = "default_time_range", length = 32)
    @Builder.Default
    private String defaultTimeRange = "24h"; // 默认时间范围
    
    @Column(name = "data_points_limit")
    @Builder.Default
    private Integer dataPointsLimit = 1000; // 数据点限制
    
    @Column(name = "alarm_sound_enabled")
    @Builder.Default
    private Boolean alarmSoundEnabled = true; // 报警声音启用
    
    @Column(name = "alarm_sound_file", length = 128)
    private String alarmSoundFile; // 报警声音文件
    
    @Column(name = "alarm_volume")
    @Builder.Default
    private Integer alarmVolume = 80; // 报警音量
    
    @Column(name = "preferred_language", length = 16)
    @Builder.Default
    private String preferredLanguage = "zh-CN"; // 偏好语言
    
    @Column(name = "timezone", length = 64)
    @Builder.Default
    private String timezone = "Asia/Shanghai"; // 时区
    
    @Column(name = "work_start_time", length = 8)
    @Builder.Default
    private String workStartTime = "09:00"; // 工作开始时间
    
    @Column(name = "work_end_time", length = 8)
    @Builder.Default
    private String workEndTime = "18:00"; // 工作结束时间
    
    @Column(name = "receive_work_hours_notifications")
    @Builder.Default
    private Boolean receiveWorkHoursNotifications = true; // 接收工作时间通知
    
    @Column(name = "receive_non_work_hours_critical_alarms")
    @Builder.Default
    private Boolean receiveNonWorkHoursCriticalAlarms = true; // 接收非工作时间关键报警
    
    @Column(name = "two_factor_method", length = 16)
    private String twoFactorMethod; // 双因素认证方法
    
    @Column(name = "two_factor_secret", length = 64)
    private String twoFactorSecret; // 双因素认证密钥
    
    @Column(name = "backup_codes", length = 1024)
    private String backupCodes; // 备份代码
    
    @Column(name = "last_password_change")
    private LocalDateTime lastPasswordChange; // 最后密码修改时间
    
    @Column(name = "password_expiry_days")
    @Builder.Default
    private Integer passwordExpiryDays = 90; // 密码过期天数
    
    @Column(name = "require_password_change")
    @Builder.Default
    private Boolean requirePasswordChange = false; // 需要密码修改
    
    @Column(name = "session_timeout_minutes")
    @Builder.Default
    private Integer sessionTimeoutMinutes = 30; // 会话超时分钟
    
    @Column(name = "concurrent_sessions_limit")
    @Builder.Default
    private Integer concurrentSessionsLimit = 3; // 并发会话限制
    
    @Column(name = "api_key", length = 64)
    private String apiKey; // API密钥
    
    @Column(name = "api_key_expiry")
    private LocalDateTime apiKeyExpiry; // API密钥过期时间
    
    @Column(name = "api_key_last_used")
    private LocalDateTime apiKeyLastUsed; // API密钥最后使用时间
    
    @Column(name = "api_usage_limit")
    @Builder.Default
    private Integer apiUsageLimit = 1000; // API使用限制
    
    @Column(name = "api_usage_count")
    @Builder.Default
    private Integer apiUsageCount = 0; // API使用计数
    
    @Column(name = "data_retention_days")
    @Builder.Default
    private Integer dataRetentionDays = 365; // 数据保留天数
    
    @Column(name = "auto_archive_enabled")
    @Builder.Default
    private Boolean autoArchiveEnabled = true; // 自动归档启用
    
    @Column(name = "export_retention_days")
    @Builder.Default
    private Integer exportRetentionDays = 30; // 导出保留天数
    
    @Column(name = "privacy_level", length = 16)
    @Builder.Default
    private String privacyLevel = "standard"; // 隐私级别
    
    @Column(name = "share_analytics")
    @Builder.Default
    private Boolean shareAnalytics = true; // 分享分析数据
    
    @Column(name = "marketing_emails")
    @Builder.Default
    private Boolean marketingEmails = false; // 营销邮件
    
    @Column(name = "product_updates")
    @Builder.Default
    private Boolean productUpdates = true; // 产品更新
    
    @Column(name = "security_notifications")
    @Builder.Default
    private Boolean securityNotifications = true; // 安全通知
    
    @Column(name = "custom_field_1", length = 255)
    private String customField1; // 自定义字段1
    
    @Column(name = "custom_field_2", length = 255)
    private String customField2; // 自定义字段2
    
    @Column(name = "custom_field_3", length = 255)
    private String customField3; // 自定义字段3
    
    @Column(name = "custom_field_4", length = 255)
    private String customField4; // 自定义字段4
    
    @Column(name = "custom_field_5", length = 255)
    private String customField5; // 自定义字段5
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 检查API密钥是否有效
     */
    public boolean isApiKeyValid() {
        if (apiKey == null || apiKey.isEmpty()) {
            return false;
        }
        if (apiKeyExpiry != null && LocalDateTime.now().isAfter(apiKeyExpiry)) {
            return false;
        }
        return true;
    }
    
    /**
     * 检查是否需要密码修改
     */
    public boolean isPasswordChangeRequired() {
        if (lastPasswordChange == null) {
            return true;
        }
        if (passwordExpiryDays > 0) {
            LocalDateTime expiryDate = lastPasswordChange.plusDays(passwordExpiryDays);
            return LocalDateTime.now().isAfter(expiryDate) || requirePasswordChange;
        }
        return requirePasswordChange;
    }
    
    /**
     * 增加API使用计数
     */
    public void incrementApiUsage() {
        this.apiUsageCount++;
    }
    
    /**
     * 重置API使用计数
     */
    public void resetApiUsageCount() {
        this.apiUsageCount = 0;
    }
    
    /**
     * 检查API使用是否超过限制
     */
    public boolean isApiUsageExceeded() {
        return apiUsageLimit > 0 && apiUsageCount >= apiUsageLimit;
    }
    
    /**
     * 获取偏好单位
     */
    public String getPreferredUnit(String dataType) {
        switch (dataType.toLowerCase()) {
            case "temperature":
                return temperatureUnit;
            case "humidity":
                return humidityUnit;
            case "pressure":
                return pressureUnit;
            default:
                return "";
        }
    }
}