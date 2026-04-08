package com.iot.temphumidity.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体类
 * 对应PostgreSQL中的users表
 * 用户分为普通用户和管理员，可以管理多个网关和设备
 */
@Entity
@Table(name = "users",
       indexes = {
           @Index(name = "idx_users_username", columnList = "username"),
           @Index(name = "idx_users_email", columnList = "email"),
           @Index(name = "idx_users_phone", columnList = "phone"),
           @Index(name = "idx_users_status", columnList = "status"),
           @Index(name = "idx_users_role", columnList = "role"),
           @Index(name = "idx_users_created_at", columnList = "created_at")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
           @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
           @UniqueConstraint(name = "uk_users_phone", columnNames = "phone")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
@EqualsAndHashCode(callSuper = false)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", length = 36)
    private String userId;
    
    @Column(name = "username", nullable = false, length = 64)
    private String username;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Column(name = "email", nullable = false, length = 128)
    private String email;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "display_name", length = 128)
    private String displayName;
    
    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 16)
    @Builder.Default
    private UserRole role = UserRole.USER;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
    
    @Column(name = "email_verified")
    @Builder.Default
    private Boolean emailVerified = false;
    
    @Column(name = "phone_verified")
    @Builder.Default
    private Boolean phoneVerified = false;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    @Column(name = "last_login_ip", length = 45) // IPv6支持
    private String lastLoginIp;
    
    @Column(name = "login_count")
    @Builder.Default
    private Integer loginCount = 0;
    
    @Column(name = "failed_login_attempts")
    @Builder.Default
    private Integer failedLoginAttempts = 0;
    
    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;
    
    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;
    
    @Column(name = "password_expires_at")
    private LocalDateTime passwordExpiresAt;
    
    @Column(name = "mfa_enabled")
    @Builder.Default
    private Boolean mfaEnabled = false;
    
    @Column(name = "mfa_secret", length = 32)
    private String mfaSecret;
    
    @Column(name = "timezone", length = 64)
    private String timezone;
    
    @Column(name = "language", length = 8)
    private String language;
    
    @Column(name = "preferences", columnDefinition = "JSONB")
    private String preferences;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @Column(name = "invitation_code", length = 32)
    private String invitationCode;
    
    @Column(name = "invited_by")
    private String invitedBy;
    
    @Column(name = "department", length = 128)
    private String department;
    
    @Column(name = "position", length = 128)
    private String position;
    
    @Column(name = "company", length = 128)
    private String company;
    
    @Column(name = "address", length = 255)
    private String address;
    
    @Column(name = "city", length = 64)
    private String city;
    
    @Column(name = "state", length = 64)
    private String state;
    
    @Column(name = "country", length = 64)
    private String country;
    
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Gateway> gateways = new ArrayList<>();
    
    /**
     * 用户角色枚举
     */
    public enum UserRole {
        SUPER_ADMIN("super_admin"), // 超级管理员
        ADMIN("admin"),             // 管理员
        USER("user"),               // 普通用户
        GUEST("guest"),             // 访客
        API_USER("api_user");       // API用户
        
        private final String value;
        
        UserRole(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static UserRole fromValue(String value) {
            for (UserRole role : UserRole.values()) {
                if (role.value.equalsIgnoreCase(value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("未知的用户角色: " + value);
        }
        
        /**
         * 检查是否有管理权限
         */
        public boolean isAdmin() {
            return this == SUPER_ADMIN || this == ADMIN;
        }
    }
    
    /**
     * 用户状态枚举
     */
    public enum UserStatus {
        ACTIVE("active"),           // 活跃
        INACTIVE("inactive"),       // 不活跃
        SUSPENDED("suspended"),     // 已暂停
        PENDING("pending"),         // 待激活
        LOCKED("locked"),           // 已锁定
        DELETED("deleted");         // 已删除
        
        private final String value;
        
        UserStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static UserStatus fromValue(String value) {
            for (UserStatus status : UserStatus.values()) {
                if (status.value.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的用户状态: " + value);
        }
    }
    
    /**
     * 检查用户是否活跃
     */
    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
    
    /**
     * 检查用户是否被锁定
     */
    public boolean isLocked() {
        if (status == UserStatus.LOCKED) {
            return true;
        }
        if (accountLockedUntil != null) {
            return LocalDateTime.now().isBefore(accountLockedUntil);
        }
        return false;
    }
    
    /**
     * 检查用户是否可登录
     */
    public boolean canLogin() {
        return isActive() && !isLocked();
    }
    
    /**
     * 检查用户是否有管理权限
     */
    public boolean isAdmin() {
        return role.isAdmin();
    }
    
    /**
     * 检查密码是否已过期
     */
    public boolean isPasswordExpired() {
        if (passwordExpiresAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(passwordExpiresAt);
    }
    
    /**
     * 检查用户是否已验证邮箱
     */
    public boolean isVerified() {
        return emailVerified;
    }
    
    /**
     * 获取用户显示名称（优先显示displayName）
     */
    public String getDisplayNameOrUsername() {
        return displayName != null && !displayName.trim().isEmpty() ? displayName : username;
    }
    
    /**
     * 获取用户角色显示名称
     */
    public String getRoleDisplay() {
        switch (role) {
            case SUPER_ADMIN: return "超级管理员";
            case ADMIN: return "管理员";
            case USER: return "普通用户";
            case GUEST: return "访客";
            case API_USER: return "API用户";
            default: return role.getValue();
        }
    }
    
    /**
     * 获取用户状态显示名称
     */
    public String getStatusDisplay() {
        switch (status) {
            case ACTIVE: return "活跃";
            case INACTIVE: return "不活跃";
            case SUSPENDED: return "已暂停";
            case PENDING: return "待激活";
            case LOCKED: return "已锁定";
            case DELETED: return "已删除";
            default: return status.getValue();
        }
    }
    
    /**
     * 获取网关数量
     */
    public int getGatewayCount() {
        return gateways != null ? gateways.size() : 0;
    }
    
    /**
     * 获取总设备数量（通过网关）
     */
    public int getTotalDeviceCount() {
        if (gateways == null) return 0;
        return gateways.stream()
            .mapToInt(gateway -> gateway.getDevices() != null ? gateway.getDevices().size() : 0)
            .sum();
    }
}
