package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

/**
 * 角色实体类
 * 对应PostgreSQL中的roles表
 */
@Entity
@Table(name = "roles", 
       indexes = {
           @Index(name = "idx_roles_name", columnList = "name"),
           @Index(name = "idx_roles_type", columnList = "role_type"),
           @Index(name = "idx_roles_system", columnList = "is_system")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_roles_name", columnNames = "name")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", length = 32)
    @Builder.Default
    private RoleType roleType = RoleType.CUSTOM;
    
    @Column(name = "is_system")
    @Builder.Default
    private Boolean isSystem = false;
    
    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;
    
    @Column(name = "permissions", columnDefinition = "TEXT")
    private String permissions;
    
    @Column(name = "weight")
    @Builder.Default
    private Integer weight = 0;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    /**
     * 角色类型枚举
     */
    public enum RoleType {
        SYSTEM("system"),      // 系统角色
        ORGANIZATION("organization"),  // 组织角色
        TEAM("team"),          // 团队角色
        PROJECT("project"),    // 项目角色
        CUSTOM("custom");      // 自定义角色
        
        private final String value;
        
        RoleType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static RoleType fromValue(String value) {
            for (RoleType type : RoleType.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("未知的角色类型: " + value);
        }
    }
    
    /**
     * 获取权限列表
     */
    public String[] getPermissionList() {
        if (permissions == null || permissions.trim().isEmpty()) {
            return new String[0];
        }
        return permissions.split(",");
    }
    
    /**
     * 检查是否包含特定权限
     */
    public boolean hasPermission(String permission) {
        String[] permissionList = getPermissionList();
        for (String perm : permissionList) {
            if (perm.trim().equals(permission)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 添加权限
     */
    public void addPermission(String permission) {
        String[] current = getPermissionList();
        for (String perm : current) {
            if (perm.equals(permission)) {
                return; // 权限已存在
            }
        }
        
        if (permissions == null || permissions.isEmpty()) {
            permissions = permission;
        } else {
            permissions += "," + permission;
        }
    }
}