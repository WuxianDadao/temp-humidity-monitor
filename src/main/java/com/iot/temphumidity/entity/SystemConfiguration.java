package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体类
 * 存储系统级别的配置参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_configurations", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"config_key", "category"})
       })
public class SystemConfiguration extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "config_key", nullable = false, length = 128)
    private String configKey;
    
    @Column(name = "config_name", nullable = false, length = 128)
    private String configName;
    
    @Column(name = "config_value", columnDefinition = "TEXT")
    private String configValue;
    
    @Column(name = "default_value", columnDefinition = "TEXT")
    private String defaultValue;
    
    @Column(name = "value_type", nullable = false, length = 32)
    private String valueType;
    
    @Column(name = "category", nullable = false, length = 64)
    private String category;
    
    @Column(name = "sub_category", length = 64)
    private String subCategory;
    
    @Column(name = "description", length = 512)
    private String description;
    
    @Column(name = "options", columnDefinition = "JSONB")
    private String options;
    
    @Column(name = "validation_rules", columnDefinition = "JSONB")
    private String validationRules;
    
    @Column(name = "is_encrypted", nullable = false)
    private Boolean isEncrypted = false;
    
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem = false;
    
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = false;
    
    @Column(name = "is_readonly", nullable = false)
    private Boolean isReadonly = false;
    
    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible = true;
    
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
    
    @Column(name = "group_name", length = 64)
    private String groupName;
    
    @Column(name = "version", nullable = false)
    private Integer version = 1;
    
    @Column(name = "effective_time")
    private java.time.LocalDateTime effectiveTime;
    
    @Column(name = "expire_time")
    private java.time.LocalDateTime expireTime;
    
    @Column(name = "scope", length = 32)
    private String scope;
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 配置类别常量
    public static final String CATEGORY_SYSTEM = "system";
    public static final String CATEGORY_DATABASE = "database";
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_NOTIFICATION = "notification";
    public static final String CATEGORY_DEVICE = "device";
    public static final String CATEGORY_MQTT = "mqtt";
    public static final String CATEGORY_TDAENGINE = "tdengine";
    public static final String CATEGORY_POSTGRESQL = "postgresql";
    public static final String CATEGORY_REDIS = "redis";
    public static final String CATEGORY_SECURITY = "security";
    public static final String CATEGORY_USER = "user";
    public static final String CATEGORY_LOG = "log";
    public static final String CATEGORY_MONITOR = "monitor";
    public static final String CATEGORY_BACKUP = "backup";
    public static final String CATEGORY_SCHEDULE = "schedule";
    public static final String CATEGORY_API = "api";
    public static final String CATEGORY_CUSTOM = "custom";
    
    // 值类型常量
    public static final String TYPE_STRING = "string";
    public static final String TYPE_INTEGER = "integer";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATETIME = "datetime";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_JSON = "json";
    public static final String TYPE_ARRAY = "array";
    public static final String TYPE_ENUM = "enum";
    public static final String TYPE_FILE = "file";
    public static final String TYPE_PASSWORD = "password";
    
    // 作用域常量
    public static final String SCOPE_GLOBAL = "global";
    public static final String SCOPE_ORGANIZATION = "organization";
    public static final String SCOPE_USER = "user";
    public static final String SCOPE_DEVICE = "device";
    public static final String SCOPE_TENANT = "tenant";
    public static final String SCOPE_PROJECT = "project";
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (valueType == null) {
            valueType = TYPE_STRING;
        }
        if (category == null) {
            category = CATEGORY_SYSTEM;
        }
        if (isEncrypted == null) {
            isEncrypted = false;
        }
        if (isSystem == null) {
            isSystem = false;
        }
        if (isRequired == null) {
            isRequired = false;
        }
        if (isReadonly == null) {
            isReadonly = false;
        }
        if (isVisible == null) {
            isVisible = true;
        }
        if (sortOrder == null) {
            sortOrder = 0;
        }
        if (version == null) {
            version = 1;
        }
        if (scope == null) {
            scope = SCOPE_GLOBAL;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
    }
    
    /**
     * 判断配置是否有效
     */
    public boolean isEffective() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        
        if (effectiveTime != null && now.isBefore(effectiveTime)) {
            return false;
        }
        
        if (expireTime != null && now.isAfter(expireTime)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 获取配置值的类型化对象
     */
    public Object getTypedValue() {
        if (configValue == null) {
            return null;
        }
        
        try {
            switch (valueType) {
                case TYPE_INTEGER:
                    return Integer.parseInt(configValue);
                case TYPE_FLOAT:
                    return Double.parseDouble(configValue);
                case TYPE_BOOLEAN:
                    return Boolean.parseBoolean(configValue);
                case TYPE_JSON:
                    return configValue; // 返回JSON字符串，调用方解析
                case TYPE_ARRAY:
                    return configValue; // 返回数组字符串，调用方解析
                case TYPE_ENUM:
                    return configValue; // 返回枚举值
                default:
                    return configValue; // 字符串类型
            }
        } catch (Exception e) {
            return defaultValue != null ? defaultValue : configValue;
        }
    }
}