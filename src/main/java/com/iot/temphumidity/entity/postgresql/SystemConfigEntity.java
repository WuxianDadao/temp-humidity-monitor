package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.entity.BaseEntity;
import com.iot.temphumidity.enums.ConfigType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 系统配置实体类 - 存储系统运行配置参数
 */
@Entity
@Table(name = "system_config", schema = "iot", indexes = {
    @Index(name = "idx_config_key", columnList = "config_key", unique = true),
    @Index(name = "idx_config_type", columnList = "config_type"),
    @Index(name = "idx_config_scope", columnList = "scope")
})
@Getter
@Setter
public class SystemConfigEntity extends BaseEntity {
    
    /**
     * 配置键
     */
    @Column(name = "config_key", nullable = false, length = 100)
    private String configKey;
    
    /**
     * 配置值
     */
    @Column(name = "config_value", length = 4000)
    private String configValue;
    
    /**
     * 配置类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "config_type", nullable = false, length = 30)
    private ConfigType configType = ConfigType.STRING;
    
    /**
     * 配置作用域
     */
    @Column(name = "scope", nullable = false, length = 50)
    private String scope = "global";
    
    /**
     * 配置描述
     */
    @Column(name = "description", length = 500)
    private String description;
    
    /**
     * 默认值
     */
    @Column(name = "default_value", length = 4000)
    private String defaultValue;
    
    /**
     * 是否只读
     */
    @Column(name = "read_only", nullable = false)
    private Boolean readOnly = false;
    
    /**
     * 是否加密存储
     */
    @Column(name = "encrypted", nullable = false)
    private Boolean encrypted = false;
    
    /**
     * 验证规则（JSON格式）
     */
    @Column(name = "validation_rules", length = 1000)
    private String validationRules;
    
    /**
     * 最后修改时间
     */
    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;
    
    /**
     * 最后修改人ID
     */
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;
    
    /**
     * 关联的修改人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity lastModifiedByUser;
    
    /**
     * 默认构造函数
     */
    public SystemConfigEntity() {
        super();
    }
    
    /**
     * 创建配置项
     */
    public SystemConfigEntity(String configKey, String configValue, String description, ConfigType configType) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
        this.configType = configType;
        this.defaultValue = configValue;
    }
    
    /**
     * 更新配置值
     */
    public void updateValue(String newValue, Long userId) {
        if (!readOnly) {
            this.configValue = newValue;
            this.lastModifiedTime = LocalDateTime.now();
            this.lastModifiedBy = userId;
        }
    }
    
    /**
     * 重置为默认值
     */
    public void resetToDefault(Long userId) {
        if (!readOnly && defaultValue != null) {
            this.configValue = defaultValue;
            this.lastModifiedTime = LocalDateTime.now();
            this.lastModifiedBy = userId;
        }
    }
    
    /**
     * 获取整数类型的配置值
     */
    public Integer getIntValue() {
        if (configValue == null) return null;
        try {
            return Integer.parseInt(configValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 获取布尔类型的配置值
     */
    public Boolean getBooleanValue() {
        if (configValue == null) return null;
        return Boolean.parseBoolean(configValue);
    }
    
    /**
     * 获取浮点数类型的配置值
     */
    public Double getDoubleValue() {
        if (configValue == null) return null;
        try {
            return Double.parseDouble(configValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 获取长整数类型的配置值
     */
    public Long getLongValue() {
        if (configValue == null) return null;
        try {
            return Long.parseLong(configValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 检查配置值是否有效（根据验证规则）
     */
    public boolean isValid() {
        // 这里可以添加验证逻辑
        // 目前只检查非空性
        if (configValue == null) return false;
        
        // 根据类型进行基本验证
        switch (configType) {
            case INTEGER:
                try {
                    Integer.parseInt(configValue);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case BOOLEAN:
                String lower = configValue.toLowerCase();
                return "true".equals(lower) || "false".equals(lower) || 
                       "1".equals(lower) || "0".equals(lower) ||
                       "yes".equals(lower) || "no".equals(lower);
            case FLOAT:
            case DOUBLE:
                try {
                    Double.parseDouble(configValue);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case LONG:
                try {
                    Long.parseLong(configValue);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case STRING:
            case JSON:
            case XML:
            case YAML:
            default:
                return true;
        }
    }
    
    /**
     * 获取配置信息
     */
    public String getConfigInfo() {
        return String.format("配置[%s] - %s (类型: %s)", 
                configKey, 
                description != null ? description : "无描述",
                configType.getDisplayName());
    }
    
    /**
     * 获取配置值（安全版本，隐藏加密值）
     */
    public String getSafeValue() {
        if (encrypted && configValue != null && configValue.length() > 0) {
            return "********";
        }
        return configValue;
    }
}