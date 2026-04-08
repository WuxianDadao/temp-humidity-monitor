package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 数据导出任务实体类
 * 管理数据导出任务
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "data_export_tasks")
public class DataExportTask extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "task_name", nullable = false, length = 128)
    private String taskName;
    
    @Column(name = "task_type", nullable = false, length = 32)
    private String taskType;
    
    @Column(name = "task_description", length = 512)
    private String taskDescription;
    
    @Column(name = "data_type", nullable = false, length = 32)
    private String dataType;
    
    @Column(name = "data_source", length = 64)
    private String dataSource;
    
    @Column(name = "query_params", columnDefinition = "JSONB")
    private String queryParams;
    
    @Column(name = "filter_conditions", columnDefinition = "JSONB")
    private String filterConditions;
    
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
    
    @Column(name = "export_format", nullable = false, length = 32)
    private String exportFormat;
    
    @Column(name = "export_columns", columnDefinition = "JSONB")
    private String exportColumns;
    
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
    
    @Column(name = "compression_format", length = 32)
    private String compressionFormat;
    
    @Column(name = "encryption_method", length = 32)
    private String encryptionMethod;
    
    @Column(name = "password", length = 128)
    private String password;
    
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "duration_seconds")
    private Long durationSeconds;
    
    @Column(name = "total_records")
    private Long totalRecords;
    
    @Column(name = "exported_records")
    private Long exportedRecords;
    
    @Column(name = "failed_records")
    private Long failedRecords;
    
    @Column(name = "progress_percent", nullable = false)
    private Integer progressPercent = 0;
    
    @Column(name = "progress_message", length = 512)
    private String progressMessage;
    
    @Column(name = "worker_node", length = 128)
    private String workerNode;
    
    @Column(name = "worker_pid")
    private Integer workerPid;
    
    @Column(name = "priority", nullable = false, length = 16)
    private String priority = "normal";
    
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
    
    @Column(name = "notify_on_complete", nullable = false)
    private Boolean notifyOnComplete = false;
    
    @Column(name = "notify_on_error", nullable = false)
    private Boolean notifyOnError = true;
    
    @Column(name = "notify_emails", columnDefinition = "JSONB")
    private String notifyEmails;
    
    @Column(name = "callback_url", length = 512)
    private String callbackUrl;
    
    @Column(name = "callback_method", length = 16)
    private String callbackMethod;
    
    @Column(name = "callback_headers", columnDefinition = "JSONB")
    private String callbackHeaders;
    
    @Column(name = "callback_payload", columnDefinition = "JSONB")
    private String callbackPayload;
    
    @Column(name = "storage_backend", length = 32)
    private String storageBackend;
    
    @Column(name = "storage_config", columnDefinition = "JSONB")
    private String storageConfig;
    
    @Column(name = "retention_days")
    private Integer retentionDays;
    
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "pending";
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 任务类型常量
    public static final String TYPE_MANUAL = "manual";
    public static final String TYPE_SCHEDULED = "scheduled";
    public static final String TYPE_AUTO = "auto";
    public static final String TYPE_ON_DEMAND = "on_demand";
    public static final String TYPE_API = "api";
    
    // 数据类型常量
    public static final String DATA_SENSOR = "sensor";
    public static final String DATA_ALARM = "alarm";
    public static final String DATA_DEVICE = "device";
    public static final String DATA_USER = "user";
    public static final String DATA_LOG = "log";
    public static final String DATA_AUDIT = "audit";
    public static final String DATA_REPORT = "report";
    public static final String DATA_STATISTICS = "statistics";
    public static final String DATA_COMPOSITE = "composite";
    
    // 数据源常量
    public static final String SOURCE_TDAENGINE = "tdengine";
    public static final String SOURCE_POSTGRESQL = "postgresql";
    public static final String SOURCE_REDIS = "redis";
    public static final String SOURCE_COMPOSITE = "composite";
    
    // 导出格式常量
    public static final String FORMAT_CSV = "csv";
    public static final String FORMAT_EXCEL = "excel";
    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";
    public static final String FORMAT_PDF = "pdf";
    public static final String FORMAT_SQL = "sql";
    public static final String FORMAT_TXT = "txt";
    
    // 压缩格式常量
    public static final String COMPRESS_ZIP = "zip";
    public static final String COMPRESS_GZIP = "gzip";
    public static final String COMPRESS_BZIP2 = "bzip2";
    public static final String COMPRESS_NONE = "none";
    
    // 加密方法常量
    public static final String ENCRYPT_AES = "aes";
    public static final String ENCRYPT_DES = "des";
    public static final String ENCRYPT_RSA = "rsa";
    public static final String ENCRYPT_NONE = "none";
    
    // 存储后端常量
    public static final String STORAGE_LOCAL = "local";
    public static final String STORAGE_S3 = "s3";
    public static final String STORAGE_OSS = "oss";
    public static final String STORAGE_MINIO = "minio";
    public static final String STORAGE_FTP = "ftp";
    public static final String STORAGE_SFTP = "sftp";
    
    // 优先级常量
    public static final String PRIORITY_URGENT = "urgent";
    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_NORMAL = "normal";
    public static final String PRIORITY_LOW = "low";
    
    // 状态常量
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_SCHEDULED = "scheduled";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_CANCELLED = "cancelled";
    public static final String STATUS_RETRYING = "retrying";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_PARTIAL = "partial";
    
    @PrePersist
    protected void onCreate() {
        if (taskType == null) {
            taskType = TYPE_MANUAL;
        }
        if (dataType == null) {
            dataType = DATA_SENSOR;
        }
        if (exportFormat == null) {
            exportFormat = FORMAT_CSV;
        }
        if (progressPercent == null) {
            progressPercent = 0;
        }
        if (priority == null) {
            priority = PRIORITY_NORMAL;
        }
        if (retryCount == null) {
            retryCount = 0;
        }
        if (maxRetries == null) {
            maxRetries = 3;
        }
        if (notifyOnComplete == null) {
            notifyOnComplete = false;
        }
        if (notifyOnError == null) {
            notifyOnError = true;
        }
        if (status == null) {
            status = STATUS_PENDING;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        // 更新时不需要特殊处理
    }
    
    /**
     * 判断是否可执行
     */
    public boolean isExecutable() {
        return STATUS_PENDING.equals(status) || 
               STATUS_SCHEDULED.equals(status) || 
               STATUS_RETRYING.equals(status);
    }
    
    /**
     * 判断是否已完成
     */
    public boolean isCompleted() {
        return STATUS_COMPLETED.equals(status);
    }
    
    /**
     * 判断是否失败且可重试
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