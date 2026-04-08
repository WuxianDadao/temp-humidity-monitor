package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.entity.BaseEntity;
import com.iot.temphumidity.enums.UserRole;
import com.iot.temphumidity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类 - 系统用户管理
 */
@Entity
@Table(name = "users", schema = "iot", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"username"}),
           @UniqueConstraint(columnNames = {"email"}),
           @UniqueConstraint(columnNames = {"phone"})
       })
@Getter
@Setter
public class UserEntity extends BaseEntity {
    
    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    
    /**
     * 密码哈希
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    /**
     * 用户邮箱
     */
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    /**
     * 手机号码
     */
    @Column(name = "phone", length = 20)
    private String phone;
    
    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 100)
    private String realName;
    
    /**
     * 用户角色
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role = UserRole.NORMAL_USER;
    
    /**
     * 用户状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;
    
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;
    
    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip", length = 50)
    private String lastLoginIp;
    
    /**
     * 登录失败次数
     */
    @Column(name = "login_fail_count", nullable = false)
    private Integer loginFailCount = 0;
    
    /**
     * 账户锁定时间
     */
    @Column(name = "locked_time")
    private LocalDateTime lockedTime;
    
    /**
     * 密码最后修改时间
     */
    @Column(name = "password_changed_time")
    private LocalDateTime passwordChangedTime;
    
    /**
     * 账户创建时间
     */
    @Column(name = "account_created_time", nullable = false, updatable = false)
    private LocalDateTime accountCreatedTime = LocalDateTime.now();
    
    /**
     * 头像URL
     */
    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;
    
    /**
     * 公司/组织名称
     */
    @Column(name = "company", length = 200)
    private String company;
    
    /**
     * 部门
     */
    @Column(name = "department", length = 100)
    private String department;
    
    /**
     * 职位
     */
    @Column(name = "position", length = 100)
    private String position;
    
    /**
     * 备注
     */
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    /**
     * 通知设置 - JSON格式存储
     */
    @Column(name = "notification_settings", length = 2000)
    private String notificationSettings;
    
    /**
     * 语言偏好
     */
    @Column(name = "language", length = 10)
    private String language = "zh-CN";
    
    /**
     * 时区
     */
    @Column(name = "timezone", length = 50)
    private String timezone = "Asia/Shanghai";
    
    /**
     * 用户拥有的网关（一对多关系）
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<GatewayEntity> gateways = new HashSet<>();
    
    /**
     * 默认构造函数
     */
    public UserEntity() {
        super();
    }
    
    /**
     * 带基础信息的构造函数
     */
    public UserEntity(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.accountCreatedTime = LocalDateTime.now();
        this.status = UserStatus.ACTIVE;
        this.role = UserRole.NORMAL_USER;
    }
    
    /**
     * 更新登录时间
     */
    public void updateLoginInfo(String ipAddress) {
        this.lastLoginTime = LocalDateTime.now();
        this.lastLoginIp = ipAddress;
        this.loginFailCount = 0; // 重置失败次数
    }
    
    /**
     * 增加登录失败次数
     */
    public void incrementLoginFailCount() {
        this.loginFailCount++;
        if (this.loginFailCount >= 5) {
            this.status = UserStatus.LOCKED;
            this.lockedTime = LocalDateTime.now();
        }
    }
    
    /**
     * 解锁账户
     */
    public void unlockAccount() {
        this.status = UserStatus.ACTIVE;
        this.loginFailCount = 0;
        this.lockedTime = null;
    }
    
    /**
     * 更新密码
     */
    public void updatePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.passwordChangedTime = LocalDateTime.now();
        this.loginFailCount = 0; // 重置失败次数
    }
    
    /**
     * 检查是否为管理员
     */
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role) || UserRole.SUPER_ADMIN.equals(this.role);
    }
    
    /**
     * 检查账户是否被锁定
     */
    public boolean isLocked() {
        return UserStatus.LOCKED.equals(this.status);
    }
    
    /**
     * 检查账户是否活跃
     */
    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.status);
    }
    
    /**
     * 获取用户显示名称
     */
    public String getDisplayName() {
        return realName != null && !realName.trim().isEmpty() ? realName : username;
    }
    
    /**
     * 获取用户基本信息
     */
    public String getUserInfo() {
        return String.format("用户[%s] %s (%s)", username, getDisplayName(), role.getDisplayName());
    }
}