package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * 数据字典实体类
 * 对应PostgreSQL中的dictionaries表
 */
@Entity
@Table(name = "dictionaries",
       indexes = {
           @Index(name = "idx_dictionaries_type", columnList = "dict_type"),
           @Index(name = "idx_dictionaries_code", columnList = "dict_type, code"),
           @Index(name = "idx_dictionaries_status", columnList = "status")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_dictionaries_unique", columnNames = {"dict_type", "code"})
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dictionary extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "dict_type", nullable = false, length = 64)
    private String dictType;  // 字典类型
    
    @Column(name = "code", nullable = false, length = 64)
    private String code;  // 字典代码
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;  // 字典名称
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // 字典描述
    
    @Column(name = "value", length = 256)
    private String value;  // 字典值
    
    @Column(name = "extra_data", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extraData;  // 额外数据 (JSON)
    
    @Column(name = "parent_id")
    private Long parentId;  // 父字典ID
    
    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;  // 排序序号
    
    @Column(name = "is_system")
    @Builder.Default
    private Boolean isSystem = false;  // 是否系统字典
    
    @Column(name = "is_editable")
    @Builder.Default
    private Boolean isEditable = true;  // 是否可编辑
    
    @Column(name = "status", length = 16)
    @Builder.Default
    private String status = "active";  // 状态
    
    @Column(name = "created_by")
    private Long createdBy;  // 创建者
    
    @Column(name = "updated_by")
    private Long updatedBy;  // 更新者
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 常用字典类型
    public enum DictType {
        // 系统基础
        GENDER("gender"),              // 性别
        YES_NO("yes_no"),              // 是否
        STATUS("status"),              // 通用状态
        
        // 设备相关
        DEVICE_TYPE("device_type"),    // 设备类型
        DEVICE_STATUS("device_status"), // 设备状态
        SENSOR_TYPE("sensor_type"),    // 传感器类型
        SENSOR_UNIT("sensor_unit"),    // 传感器单位
        
        // 通信相关
        COMMUNICATION_PROTOCOL("communication_protocol"),  // 通信协议
        NETWORK_TYPE("network_type"),  // 网络类型
        
        // 报警相关
        ALARM_SEVERITY("alarm_severity"),  // 报警严重程度
        ALARM_STATUS("alarm_status"),      // 报警状态
        ALARM_TYPE("alarm_type"),          // 报警类型
        
        // 业务相关
        ORGANIZATION_TYPE("organization_type"),  // 组织类型
        TENANT_STATUS("tenant_status"),          // 租户状态
        USER_ROLE("user_role"),                  // 用户角色
        PERMISSION_TYPE("permission_type");      // 权限类型
        
        private final String value;
        
        DictType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 状态枚举
    public enum Status {
        ACTIVE("active"),
        INACTIVE("inactive"),
        ARCHIVED("archived");
        
        private final String value;
        
        Status(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 检查是否为系统字典
    public boolean isSystemDictionary() {
        return Boolean.TRUE.equals(isSystem);
    }
    
    // 检查是否可编辑
    public boolean isEditableDictionary() {
        return Boolean.TRUE.equals(isEditable);
    }
    
    // 获取完整路径
    public String getFullPath() {
        return dictType + "." + code;
    }
    
    // 检查是否有子项
    public boolean hasChildren() {
        // 这个方法需要在服务层实现，这里只是占位符
        return false;
    }
    
    // 预持久化回调
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }
    
    // 预更新回调
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}