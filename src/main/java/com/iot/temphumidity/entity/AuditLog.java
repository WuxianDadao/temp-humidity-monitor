package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 审计日志实体类
 * 记录系统操作和事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "audit_id", nullable = false, unique = true, length = 64)
    private String auditId;
    
    @Column(name = "event_type", nullable = false, length = 32)
    private String eventType;
    
    @Column(name = "event_category", nullable = false, length = 32)
    private String eventCategory;
    
    @Column(name = "event_name", nullable = false, length = 128)
    private String eventName;
    
    @Column(name = "event_description", length = 512)
    private String eventDescription;
    
    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;
    
    @Column(name = "user_id", length = 64)
    private String userId;
    
    @Column(name = "user_name", length = 128)
    private String userName;
    
    @Column(name = "user_email", length = 128)
    private String userEmail;
    
    @Column(name = "user_phone", length = 32)
    private String userPhone;
    
    @Column(name = "user_role", length = 32)
    private String userRole;
    
    @Column(name = "user_department", length = 128)
    private String userDepartment;
    
    @Column(name = "user_ip", length = 45)
    private String userIp;
    
    @Column(name = "user_agent", length = 512)
    private String userAgent;
    
    @Column(name = "user_location", length = 256)
    private String userLocation;
    
    @Column(name = "client_type", length = 32)
    private String clientType;
    
    @Column(name = "client_version", length = 32)
    private String clientVersion;
    
    @Column(name = "client_device", length = 128)
    private String clientDevice;
    
    @Column(name = "client_os", length = 64)
    private String clientOs;
    
    @Column(name = "client_browser", length = 64)
    private String clientBrowser;
    
    @Column(name = "organization_id", length = 64)
    private String organizationId;
    
    @Column(name = "organization_name", length = 128)
    private String organizationName;
    
    @Column(name = "resource_type", length = 32)
    private String resourceType;
    
    @Column(name = "resource_id", length = 64)
    private String resourceId;
    
    @Column(name = "resource_name", length = 128)
    private String resourceName;
    
    @Column(name = "resource_path", length = 512)
    private String resourcePath;
    
    @Column(name = "resource_url", length = 512)
    private String resourceUrl;
    
    @Column(name = "operation", nullable = false, length = 32)
    private String operation;
    
    @Column(name = "operation_target", length = 128)
    private String operationTarget;
    
    @Column(name = "operation_details", columnDefinition = "JSONB")
    private String operationDetails;
    
    @Column(name = "request_method", length = 16)
    private String requestMethod;
    
    @Column(name = "request_url", length = 1024)
    private String requestUrl;
    
    @Column(name = "request_path", length = 512)
    private String requestPath;
    
    @Column(name = "request_query", length = 1024)
    private String requestQuery;
    
    @Column(name = "request_headers", columnDefinition = "JSONB")
    private String requestHeaders;
    
    @Column(name = "request_body", columnDefinition = "TEXT")
    private String requestBody;
    
    @Column(name = "request_size_bytes")
    private Long requestSizeBytes;
    
    @Column(name = "response_status_code")
    private Integer responseStatusCode;
    
    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;
    
    @Column(name = "response_size_bytes")
    private Long responseSizeBytes;
    
    @Column(name = "response_time_ms")
    private Long responseTimeMs;
    
    @Column(name = "execution_time_ms")
    private Long executionTimeMs;
    
    @Column(name = "is_success", nullable = false)
    private Boolean isSuccess = true;
    
    @Column(name = "is_secure", nullable = false)
    private Boolean isSecure = true;
    
    @Column(name = "is_authenticated", nullable = false)
    private Boolean isAuthenticated = false;
    
    @Column(name = "is_authorized", nullable = false)
    private Boolean isAuthorized = false;
    
    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = true;
    
    @Column(name = "session_id", length = 64)
    private String sessionId;
    
    @Column(name = "correlation_id", length = 64)
    private String correlationId;
    
    @Column(name = "trace_id", length = 64)
    private String traceId;
    
    @Column(name = "span_id", length = 64)
    private String spanId;
    
    @Column(name = "parent_span_id", length = 64)
    private String parentSpanId;
    
    @Column(name = "service_name", length = 64)
    private String serviceName;
    
    @Column(name = "service_instance", length = 128)
    private String serviceInstance;
    
    @Column(name = "service_version", length = 32)
    private String serviceVersion;
    
    @Column(name = "host_name", length = 128)
    private String hostName;
    
    @Column(name = "host_ip", length = 45)
    private String hostIp;
    
    @Column(name = "host_port")
    private Integer hostPort;
    
    @Column(name = "tenant_id", length = 64)
    private String tenantId;
    
    @Column(name = "tenant_name", length = 128)
    private String tenantName;
    
    @Column(name = "domain_id", length = 64)
    private String domainId;
    
    @Column(name = "domain_name", length = 128)
    private String domainName;
    
    @Column(name = "project_id", length = 64)
    private String projectId;
    
    @Column(name = "project_name", length = 128)
    private String projectName;
    
    @Column(name = "environment", length = 32)
    private String environment;
    
    @Column(name = "severity", nullable = false, length = 16)
    private String severity = "info";
    
    @Column(name = "risk_level", length = 16)
    private String riskLevel;
    
    @Column(name = "risk_score")
    private Integer riskScore;
    
    @Column(name = "compliance_standard", length = 64)
    private String complianceStandard;
    
    @Column(name = "compliance_requirement", length = 128)
    private String complianceRequirement;
    
    @Column(name = "compliance_violation", nullable = false)
    private Boolean complianceViolation = false;
    
    @Column(name = "data_changed", columnDefinition = "JSONB")
    private String dataChanged;
    
    @Column(name = "old_values", columnDefinition = "JSONB")
    private String oldValues;
    
    @Column(name = "new_values", columnDefinition = "JSONB")
    private String newValues;
    
    @Column(name = "delta_values", columnDefinition = "JSONB")
    private String deltaValues;
    
    @Column(name = "affected_records", columnDefinition = "JSONB")
    private String affectedRecords;
    
    @Column(name = "affected_count")
    private Integer affectedCount;
    
    @Column(name = "error_code", length = 32)
    private String errorCode;
    
    @Column(name = "error_message", length = 1024)
    private String errorMessage;
    
    @Column(name = "error_stack_trace", columnDefinition = "TEXT")
    private String errorStackTrace;
    
    @Column(name = "error_details", columnDefinition = "JSONB")
    private String errorDetails;
    
    @Column(name = "exception_type", length = 128)
    private String exceptionType;
    
    @Column(name = "exception_class", length = 256)
    private String exceptionClass;
    
    @Column(name = "exception_method", length = 128)
    private String exceptionMethod;
    
    @Column(name = "exception_line")
    private Integer exceptionLine;
    
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;
    
    @Column(name = "retry_reason", length = 256)
    private String retryReason;
    
    @Column(name = "is_retry", nullable = false)
    private Boolean isRetry = false;
    
    @Column(name = "is_duplicate", nullable = false)
    private Boolean isDuplicate = false;
    
    @Column(name = "duplicate_of_id")
    private Long duplicateOfId;
    
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;
    
    @Column(name = "archive_time")
    private LocalDateTime archiveTime;
    
    @Column(name = "archive_path", length = 512)
    private String archivePath;
    
    @Column(name = "retention_days")
    private Integer retentionDays;
    
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 事件类型常量
    public static final String EVENT_LOGIN = "login";
    public static final String EVENT_LOGOUT = "logout";
    public static final String EVENT_CREATE = "create";
    public static final String EVENT_READ = "read";
    public static final String EVENT_UPDATE = "update";
    public static final String EVENT_DELETE = "delete";
    public static final String EVENT_EXPORT = "export";
    public static final String EVENT_IMPORT = "import";
    public static final String EVENT_UPLOAD = "upload";
    public static final String EVENT_DOWNLOAD = "download";
    public static final String EVENT_EXECUTE = "execute";
    public static final String EVENT_ACCESS = "access";
    public static final String EVENT_CONFIG_CHANGE = "config_change";
    public static final String EVENT_PERMISSION_CHANGE = "permission_change";
    public static final String EVENT_ROLE_CHANGE = "role_change";
    public static final String EVENT_ALARM_TRIGGER = "alarm_trigger";
    public static final String EVENT_ALARM_CLEAR = "alarm_clear";
    public static final String EVENT_SYSTEM_START = "system_start";
    public static final String EVENT_SYSTEM_STOP = "system_stop";
    public static final String EVENT_SYSTEM_ERROR = "system_error";
    public static final String EVENT_BACKUP = "backup";
    public static final String EVENT_RESTORE = "restore";
    public static final String EVENT_SCHEDULED_TASK = "scheduled_task";
    public static final String EVENT_API_CALL = "api_call";
    public static final String EVENT_WEBHOOK = "webhook";
    public static final String EVENT_MQTT = "mqtt";
    public static final String EVENT_DATABASE = "database";
    public static final String EVENT_CACHE = "cache";
    public static final String EVENT_FILE = "file";
    public static final String EVENT_EMAIL = "email";
    public static final String EVENT_SMS = "sms";
    public static final String EVENT_NOTIFICATION = "notification";
    public static final String EVENT_AUDIT = "audit";
    public static final String EVENT_SECURITY = "security";
    public static final String EVENT_COMPLIANCE = "compliance";
    public static final String EVENT_BUSINESS = "business";
    
    // 事件分类常量
    public static final String CATEGORY_AUTHENTICATION = "authentication";
    public static final String CATEGORY_AUTHORIZATION = "authorization";
    public static final String CATEGORY_ACCESS_CONTROL = "access_control";
    public static final String CATEGORY_DATA_OPERATION = "data_operation";
    public static final String CATEGORY_SYSTEM_OPERATION = "system_operation";
    public static final String CATEGORY_CONFIGURATION = "configuration";
    public static final String CATEGORY_MONITORING = "monitoring";
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_NOTIFICATION = "notification";
    public static final String CATEGORY_COMMUNICATION = "communication";
    public static final String CATEGORY_INTEGRATION = "integration";
    public static final String CATEGORY_BACKUP = "backup";
    public static final String CATEGORY_SECURITY = "security";
    public static final String CATEGORY_COMPLIANCE = "compliance";
    public static final String CATEGORY_BUSINESS = "business";
    public static final String CATEGORY_AUDIT = "audit";
    public static final String CATEGORY_PERFORMANCE = "performance";
    public static final String CATEGORY_AVAILABILITY = "availability";
    
    // 操作常量
    public static final String OPERATION_CREATE = "create";
    public static final String OPERATION_READ = "read";
    public static final String OPERATION_UPDATE = "update";
    public static final String OPERATION_DELETE = "delete";
    public static final String OPERATION_EXPORT = "export";
    public static final String OPERATION_IMPORT = "import";
    public static final String OPERATION_UPLOAD = "upload";
    public static final String OPERATION_DOWNLOAD = "download";
    public static final String OPERATION_EXECUTE = "execute";
    public static final String OPERATION_ACCESS = "access";
    public static final String OPERATION_LOGIN = "login";
    public static final String OPERATION_LOGOUT = "logout";
    public static final String OPERATION_CONFIG = "config";
    public static final String OPERATION_PERMISSION = "permission";
    public static final String OPERATION_ROLE = "role";
    public static final String OPERATION_TRIGGER = "trigger";
    public static final String OPERATION_CLEAR = "clear";
    public static final String OPERATION_START = "start";
    public static final String OPERATION_STOP = "stop";
    public static final String OPERATION_RESTART = "restart";
    public static final String OPERATION_BACKUP = "backup";
    public static final String OPERATION_RESTORE = "restore";
    public static final String OPERATION_SCHEDULE = "schedule";
    public static final String OPERATION_CALL = "call";
    public static final String OPERATION_SEND = "send";
    public static final String OPERATION_RECEIVE = "receive";
    public static final String OPERATION_PUBLISH = "publish";
    public static final String OPERATION_SUBSCRIBE = "subscribe";
    public static final String OPERATION_INSERT = "insert";
    public static final String OPERATION_SELECT = "select";
    public static final String OPERATION_UPDATE_DB = "update_db";
    public static final String OPERATION_DELETE_DB = "delete_db";
    public static final String OPERATION_CLEAR_CACHE = "clear_cache";
    public static final String OPERATION_INVALIDATE = "invalidate";
    
    // 资源类型常量
    public static final String RESOURCE_USER = "user";
    public static final String RESOURCE_ROLE = "role";
    public static final String RESOURCE_PERMISSION = "permission";
    public static final String RESOURCE_ORGANIZATION = "organization";
    public static final String RESOURCE_DEVICE = "device";
    public static final String RESOURCE_GATEWAY = "gateway";
    public static final String RESOURCE_SENSOR = "sensor";
    public static final String RESOURCE_ALARM = "alarm";
    public static final String RESOURCE_ALARM_RULE = "alarm_rule";
    public static final String RESOURCE_SENSOR_DATA = "sensor_data";
    public static final String RESOURCE_NOTIFICATION = "notification";
    public static final String RESOURCE_REPORT = "report";
    public static final String RESOURCE_DASHBOARD = "dashboard";
    public static final String RESOURCE_CONFIG = "config";
    public static final String RESOURCE_SYSTEM = "system";
    public static final String RESOURCE_API = "api";
    public static final String RESOURCE_FILE = "file";
    public static final String RESOURCE_DATABASE = "database";
    public static final String RESOURCE_CACHE = "cache";
    public static final String RESOURCE_QUEUE = "queue";
    public static final String RESOURCE_SCHEDULE = "schedule";
    public static final String RESOURCE_BACKUP = "backup";
    public static final String RESOURCE_LOG = "log";
    public static final String RESOURCE_AUDIT = "audit";
    public static final String RESOURCE_MONITOR = "monitor";
    public static final String RESOURCE_COMPLIANCE = "compliance";
    
    // 严重程度常量
    public static final String SEVERITY_DEBUG = "debug";
    public static final String SEVERITY_INFO = "info";
    public static final String SEVERITY_WARNING = "warning";
    public static final String SEVERITY_ERROR = "error";
    public static final String SEVERITY_CRITICAL = "critical";
    public static final String SEVERITY_FATAL = "fatal";
    
    // 风险等级常量
    public static final String RISK_LOW = "low";
    public static final String RISK_MEDIUM = "medium";
    public static final String RISK_HIGH = "high";
    public static final String RISK_CRITICAL = "critical";
}