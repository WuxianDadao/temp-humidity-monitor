package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 系统日志实体类
 * 记录系统运行日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_logs", indexes = {
    @Index(name = "idx_system_logs_level", columnList = "level"),
    @Index(name = "idx_system_logs_module", columnList = "module"),
    @Index(name = "idx_system_logs_created_at", columnList = "created_at"),
    @Index(name = "idx_system_logs_trace_id", columnList = "trace_id")
})
public class SystemLog extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "trace_id", length = 64)
    private String traceId;
    
    @Column(name = "span_id", length = 64)
    private String spanId;
    
    @Column(name = "parent_span_id", length = 64)
    private String parentSpanId;
    
    @Column(name = "level", nullable = false, length = 16)
    private String level;
    
    @Column(name = "logger_name", nullable = false, length = 256)
    private String loggerName;
    
    @Column(name = "module", nullable = false, length = 64)
    private String module;
    
    @Column(name = "sub_module", length = 64)
    private String subModule;
    
    @Column(name = "operation", length = 128)
    private String operation;
    
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "exception_class", length = 256)
    private String exceptionClass;
    
    @Column(name = "exception_message", columnDefinition = "TEXT")
    private String exceptionMessage;
    
    @Column(name = "exception_stack", columnDefinition = "TEXT")
    private String exceptionStack;
    
    @Column(name = "thread_name", length = 128)
    private String threadName;
    
    @Column(name = "host_name", length = 128)
    private String hostName;
    
    @Column(name = "host_ip", length = 64)
    private String hostIp;
    
    @Column(name = "service_name", length = 128)
    private String serviceName;
    
    @Column(name = "service_version", length = 32)
    private String serviceVersion;
    
    @Column(name = "instance_id", length = 64)
    private String instanceId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "username", length = 64)
    private String username;
    
    @Column(name = "user_agent", length = 512)
    private String userAgent;
    
    @Column(name = "client_ip", length = 64)
    private String clientIp;
    
    @Column(name = "request_id", length = 64)
    private String requestId;
    
    @Column(name = "request_method", length = 16)
    private String requestMethod;
    
    @Column(name = "request_uri", length = 512)
    private String requestUri;
    
    @Column(name = "request_params", columnDefinition = "TEXT")
    private String requestParams;
    
    @Column(name = "request_headers", columnDefinition = "JSONB")
    private String requestHeaders;
    
    @Column(name = "response_status")
    private Integer responseStatus;
    
    @Column(name = "response_time_ms")
    private Long responseTimeMs;
    
    @Column(name = "response_size")
    private Long responseSize;
    
    @Column(name = "sql_statement", columnDefinition = "TEXT")
    private String sqlStatement;
    
    @Column(name = "sql_params", columnDefinition = "JSONB")
    private String sqlParams;
    
    @Column(name = "sql_execution_time_ms")
    private Long sqlExecutionTimeMs;
    
    @Column(name = "sql_rows_affected")
    private Long sqlRowsAffected;
    
    @Column(name = "mqtt_topic", length = 512)
    private String mqttTopic;
    
    @Column(name = "mqtt_payload", columnDefinition = "TEXT")
    private String mqttPayload;
    
    @Column(name = "mqtt_qos")
    private Integer mqttQos;
    
    @Column(name = "device_id")
    private Long deviceId;
    
    @Column(name = "device_sn", length = 128)
    private String deviceSn;
    
    @Column(name = "sensor_id")
    private Long sensorId;
    
    @Column(name = "sensor_type", length = 64)
    private String sensorType;
    
    @Column(name = "data_value", length = 128)
    private String dataValue;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "log_time", nullable = false)
    private LocalDateTime logTime = LocalDateTime.now();
    
    @Column(name = "retention_days")
    private Integer retentionDays;
    
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;
    
    @Column(name = "archive_time")
    private LocalDateTime archiveTime;
    
    @Column(name = "archive_path", length = 512)
    private String archivePath;
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 日志级别常量
    public static final String LEVEL_TRACE = "trace";
    public static final String LEVEL_DEBUG = "debug";
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_WARN = "warn";
    public static final String LEVEL_ERROR = "error";
    public static final String LEVEL_FATAL = "fatal";
    
    // 模块常量
    public static final String MODULE_SYSTEM = "system";
    public static final String MODULE_USER = "user";
    public static final String MODULE_AUTH = "auth";
    public static final String MODULE_DEVICE = "device";
    public static final String MODULE_SENSOR = "sensor";
    public static final String MODULE_ALARM = "alarm";
    public static final String MODULE_NOTIFICATION = "notification";
    public static final String MODULE_MQTT = "mqtt";
    public static final String MODULE_TDAENGINE = "tdengine";
    public static final String MODULE_POSTGRESQL = "postgresql";
    public static final String MODULE_REDIS = "redis";
    public static final String MODULE_API = "api";
    public static final String MODULE_SCHEDULE = "schedule";
    public static final String MODULE_BACKUP = "backup";
    public static final String MODULE_MONITOR = "monitor";
    public static final String MODULE_SECURITY = "security";
    public static final String MODULE_BUSINESS = "business";
    
    // 操作常量
    public static final String OPERATION_LOGIN = "login";
    public static final String OPERATION_LOGOUT = "logout";
    public static final String OPERATION_CREATE = "create";
    public static final String OPERATION_UPDATE = "update";
    public static final String OPERATION_DELETE = "delete";
    public static final String OPERATION_QUERY = "query";
    public static final String OPERATION_EXPORT = "export";
    public static final String OPERATION_IMPORT = "import";
    public static final String OPERATION_UPLOAD = "upload";
    public static final String OPERATION_DOWNLOAD = "download";
    public static final String OPERATION_START = "start";
    public static final String OPERATION_STOP = "stop";
    public static final String OPERATION_RESTART = "restart";
    public static final String OPERATION_CONNECT = "connect";
    public static final String OPERATION_DISCONNECT = "disconnect";
    public static final String OPERATION_SEND = "send";
    public static final String OPERATION_RECEIVE = "receive";
    public static final String OPERATION_PUBLISH = "publish";
    public static final String OPERATION_SUBSCRIBE = "subscribe";
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (level == null) {
            level = LEVEL_INFO;
        }
        if (module == null) {
            module = MODULE_SYSTEM;
        }
        if (logTime == null) {
            logTime = LocalDateTime.now();
        }
        if (isArchived == null) {
            isArchived = false;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
    }
    
    /**
     * 判断是否错误级别日志
     */
    public boolean isErrorLevel() {
        return LEVEL_ERROR.equals(level) || 
               LEVEL_FATAL.equals(level) || 
               LEVEL_WARN.equals(level);
    }
    
    /**
     * 判断是否应该归档
     */
    public boolean shouldArchive() {
        if (isArchived) {
            return false;
        }
        
        if (retentionDays != null && retentionDays > 0) {
            LocalDateTime retentionDate = logTime.plusDays(retentionDays);
            return LocalDateTime.now().isAfter(retentionDate);
        }
        
        return false;
    }
}