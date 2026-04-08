package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 数据报告实体类
 * 管理数据报告
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "data_reports")
public class DataReport extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_name", nullable = false, length = 128)
    private String reportName;
    
    @Column(name = "report_type", nullable = false, length = 32)
    private String reportType;
    
    @Column(name = "report_description", length = 512)
    private String reportDescription;
    
    @Column(name = "data_type", nullable = false, length = 32)
    private String dataType;
    
    @Column(name = "report_period", length = 32)
    private String reportPeriod;
    
    @Column(name = "time_range_start")
    private LocalDateTime timeRangeStart;
    
    @Column(name = "time_range_end")
    private LocalDateTime timeRangeEnd;
    
    @Column(name = "device_ids", columnDefinition = "JSONB")
    private String deviceIds;
    
    @Column(name = "sensor_ids", columnDefinition = "JSONB")
    private String sensorIds;
    
    @Column(name = "organization_ids", columnDefinition = "JSONB")
    private String organizationIds;
    
    @Column(name = "report_template", length = 256)
    private String reportTemplate;
    
    @Column(name = "report_config", columnDefinition = "JSONB")
    private String reportConfig;
    
    @Column(name = "report_format", nullable = false, length = 32)
    private String reportFormat;
    
    @Column(name = "file_name", length = 256)
    private String fileName;
    
    @Column(name = "file_path", length = 512)
    private String filePath;
    
    @Column(name = "file_url", length = 512)
    private String fileUrl;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "file_md5", length = 64)
    private String fileMd5;
    
    @Column(name = "file_mime_type", length = 128)
    private String fileMimeType;
    
    @Column(name = "generation_time")
    private LocalDateTime generationTime;
    
    @Column(name = "generation_duration_seconds")
    private Long generationDurationSeconds;
    
    @Column(name = "generated_by", length = 64)
    private String generatedBy;
    
    @Column(name = "generation_method", length = 32)
    private String generationMethod;
    
    @Column(name = "data_count")
    private Long dataCount;
    
    @Column(name = "data_volume_bytes")
    private Long dataVolumeBytes;
    
    @Column(name = "summary_data", columnDefinition = "JSONB")
    private String summaryData;
    
    @Column(name = "statistics_data", columnDefinition = "JSONB")
    private String statisticsData;
    
    @Column(name = "charts_data", columnDefinition = "JSONB")
    private String chartsData;
    
    @Column(name = "tables_data", columnDefinition = "JSONB")
    private String tablesData;
    
    @Column(name = "alerts_data", columnDefinition = "JSONB")
    private String alertsData;
    
    @Column(name = "recommendations", columnDefinition = "JSONB")
    private String recommendations;
    
    @Column(name = "is_scheduled", nullable = false)
    private Boolean isScheduled = false;
    
    @Column(name = "schedule_id")
    private Long scheduleId;
    
    @Column(name = "schedule_name", length = 128)
    private String scheduleName;
    
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;
    
    @Column(name = "access_level", length = 16)
    private String accessLevel;
    
    @Column(name = "allowed_users", columnDefinition = "JSONB")
    private String allowedUsers;
    
    @Column(name = "allowed_roles", columnDefinition = "JSONB")
    private String allowedRoles;
    
    @Column(name = "allowed_organizations", columnDefinition = "JSONB")
    private String allowedOrganizations;
    
    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;
    
    @Column(name = "download_count", nullable = false)
    private Long downloadCount = 0L;
    
    @Column(name = "last_view_time")
    private LocalDateTime lastViewTime;
    
    @Column(name = "last_view_by", length = 64)
    private String lastViewBy;
    
    @Column(name = "last_download_time")
    private LocalDateTime lastDownloadTime;
    
    @Column(name = "last_download_by", length = 64)
    private String lastDownloadBy;
    
    @Column(name = "notify_on_generation", nullable = false)
    private Boolean notifyOnGeneration = false;
    
    @Column(name = "notify_emails", columnDefinition = "JSONB")
    private String notifyEmails;
    
    @Column(name = "notify_webhooks", columnDefinition = "JSONB")
    private String notifyWebhooks;
    
    @Column(name = "storage_backend", length = 32)
    private String storageBackend;
    
    @Column(name = "storage_config", columnDefinition = "JSONB")
    private String storageConfig;
    
    @Column(name = "retention_days")
    private Integer retentionDays;
    
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;
    
    @Column(name = "archive_time")
    private LocalDateTime archiveTime;
    
    @Column(name = "archive_path", length = 512)
    private String archivePath;
    
    @Column(name = "version", nullable = false)
    private Integer version = 1;
    
    @Column(name = "previous_version_id")
    private Long previousVersionId;
    
    @Column(name = "is_latest", nullable = false)
    private Boolean isLatest = true;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "draft";
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 报告类型常量
    public static final String TYPE_DAILY = "daily";
    public static final String TYPE_WEEKLY = "weekly";
    public static final String TYPE_MONTHLY = "monthly";
    public static final String TYPE_QUARTERLY = "quarterly";
    public static final String TYPE_YEARLY = "yearly";
    public static final String TYPE_CUSTOM = "custom";
    public static final String TYPE_ON_DEMAND = "on_demand";
    public static final String TYPE_ALERT = "alert";
    public static final String TYPE_SUMMARY = "summary";
    public static final String TYPE_DETAILED = "detailed";
    public static final String TYPE_COMPARATIVE = "comparative";
    public static final String TYPE_TREND = "trend";
    public static final String TYPE_PERFORMANCE = "performance";
    public static final String TYPE_COMPLIANCE = "compliance";
    public static final String TYPE_AUDIT = "audit";
    
    // 数据类型常量
    public static final String DATA_SENSOR = "sensor";
    public static final String DATA_ALARM = "alarm";
    public static final String DATA_DEVICE = "device";
    public static final String DATA_USER = "user";
    public static final String DATA_LOG = "log";
    public static final String DATA_AUDIT = "audit";
    public static final String DATA_SYSTEM = "system";
    public static final String DATA_BUSINESS = "business";
    public static final String DATA_COMPOSITE = "composite";
    
    // 报告周期常量
    public static final String PERIOD_DAY = "day";
    public static final String PERIOD_WEEK = "week";
    public static final String PERIOD_MONTH = "month";
    public static final String PERIOD_QUARTER = "quarter";
    public static final String PERIOD_YEAR = "year";
    public static final String PERIOD_CUSTOM = "custom";
    
    // 报告格式常量
    public static final String FORMAT_PDF = "pdf";
    public static final String FORMAT_EXCEL = "excel";
    public static final String FORMAT_HTML = "html";
    public static final String FORMAT_CSV = "csv";
    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";
    public static final String FORMAT_WORD = "word";
    public static final String FORMAT_POWERPOINT = "powerpoint";
    
    // 生成方法常量
    public static final String METHOD_AUTO = "auto";
    public static final String METHOD_MANUAL = "manual";
    public static final String METHOD_SCHEDULED = "scheduled";
    public static final String METHOD_API = "api";
    
    // 访问级别常量
    public static final String ACCESS_PUBLIC = "public";
    public static final String ACCESS_PRIVATE = "private";
    public static final String ACCESS_RESTRICTED = "restricted";
    public static final String ACCESS_CONFIDENTIAL = "confidential";
    
    // 存储后端常量
    public static final String STORAGE_LOCAL = "local";
    public static final String STORAGE_S3 = "s3";
    public static final String STORAGE_OSS = "oss";
    public static final String STORAGE_MINIO = "minio";
    public static final String STORAGE_FTP = "ftp";
    public static final String STORAGE_SFTP = "sftp";
    
    // 状态常量
    public static final String STATUS_DRAFT = "draft";
    public static final String STATUS_GENERATING = "generating";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_ARCHIVED = "archived";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_PUBLISHED = "published";
    public static final String STATUS_UNPUBLISHED = "unpublished";
    
    @PrePersist
    protected void onCreate() {
        // 创建前的初始化逻辑
        if (reportType == null) {
            reportType = TYPE_CUSTOM;
        }
        if (dataType == null) {
            dataType = DATA_SENSOR;
        }
        if (reportFormat == null) {
            reportFormat = FORMAT_PDF;
        }
        if (isScheduled == null) {
            isScheduled = false;
        }
        if (isPublic == null) {
            isPublic = false;
        }
        if (viewCount == null) {
            viewCount = 0L;
        }
        if (downloadCount == null) {
            downloadCount = 0L;
        }
        if (notifyOnGeneration == null) {
            notifyOnGeneration = false;
        }
        if (isArchived == null) {
            isArchived = false;
        }
        if (version == null) {
            version = 1;
        }
        if (isLatest == null) {
            isLatest = true;
        }
        if (status == null) {
            status = STATUS_DRAFT;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        // 更新前的逻辑
    }
    
    /**
     * 判断是否可查看
     */
    public boolean isViewable() {
        return STATUS_COMPLETED.equals(status) || 
               STATUS_PUBLISHED.equals(status);
    }
    
    /**
     * 判断是否可下载
     */
    public boolean isDownloadable() {
        return STATUS_COMPLETED.equals(status) || 
               STATUS_PUBLISHED.equals(status);
    }
    
    /**
     * 判断是否已过期
     */
    public boolean isExpired() {
        return expireTime != null && LocalDateTime.now().isAfter(expireTime);
    }
    
    /**
     * 增加查看次数
     */
    public void incrementViewCount() {
        this.viewCount = this.viewCount + 1;
        this.lastViewTime = LocalDateTime.now();
    }
    
    /**
     * 增加下载次数
     */
    public void incrementDownloadCount() {
        this.downloadCount = this.downloadCount + 1;
        this.lastDownloadTime = LocalDateTime.now();
    }
}