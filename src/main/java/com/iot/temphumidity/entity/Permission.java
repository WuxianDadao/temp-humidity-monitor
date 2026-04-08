package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 权限实体类
 * 存储系统权限信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "permission_code", nullable = false, unique = true, length = 64)
    private String permissionCode;
    
    @Column(name = "permission_name", nullable = false, length = 128)
    private String permissionName;
    
    @Column(name = "description", length = 512)
    private String description;
    
    @Column(name = "resource_type", nullable = false, length = 32)
    private String resourceType;
    
    @Column(name = "resource_pattern", nullable = false, length = 256)
    private String resourcePattern;
    
    @Column(name = "action", nullable = false, length = 32)
    private String action;
    
    @Column(name = "category", length = 64)
    private String category;
    
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
    
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem = false;
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Column(name = "level_path", length = 256)
    private String levelPath;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "active";
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    @Column(name = "created_by", length = 64)
    private String createdBy;
    
    @Column(name = "updated_by", length = 64)
    private String updatedBy;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    // 预定义状态常量
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_INACTIVE = "inactive";
    public static final String STATUS_DELETED = "deleted";
    
    // 资源类型常量
    public static final String RESOURCE_TYPE_MENU = "menu";
    public static final String RESOURCE_TYPE_BUTTON = "button";
    public static final String RESOURCE_TYPE_API = "api";
    public static final String RESOURCE_TYPE_DATA = "data";
    
    // 操作类型常量
    public static final String ACTION_VIEW = "view";
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_EXECUTE = "execute";
    public static final String ACTION_EXPORT = "export";
    public static final String ACTION_IMPORT = "import";
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (sortOrder == null) {
            sortOrder = 0;
        }
        if (isSystem == null) {
            isSystem = false;
        }
        if (status == null) {
            status = STATUS_ACTIVE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
    }
}