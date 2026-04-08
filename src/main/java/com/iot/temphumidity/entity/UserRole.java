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
 * 用户角色关联实体类
 * 对应PostgreSQL中的user_roles表
 */
@Entity
@Table(name = "user_roles", 
       indexes = {
           @Index(name = "idx_user_roles_user_id", columnList = "user_id"),
           @Index(name = "idx_user_roles_role_id", columnList = "role_id"),
           @Index(name = "idx_user_roles_unique", columnList = "user_id, role_id", unique = true)
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class UserRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_roles_user_id"))
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_roles_role_id"))
    private Role role;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", foreignKey = @ForeignKey(name = "fk_user_roles_assigned_by"))
    private User assignedBy;
    
    @Column(name = "scope_type", length = 32)
    private String scopeType;
    
    @Column(name = "scope_id")
    private Long scopeId;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    /**
     * 检查角色是否已过期
     */
    public boolean isExpired() {
        if (expiresAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    /**
     * 获取完整的scope标识
     */
    public String getFullScope() {
        if (scopeType == null || scopeId == null) {
            return null;
        }
        return scopeType + ":" + scopeId;
    }
}