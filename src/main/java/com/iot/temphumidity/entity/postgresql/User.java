package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username", unique = true),
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_role", columnList = "role"),
    @Index(name = "idx_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(nullable = false, length = 100)
    private String password;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(length = 50)
    private String realName;
    
    @Column(length = 20)
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.VIEWER;
    
    @Column(nullable = false, length = 20)
    private String status = "active"; // active, disabled, locked
    
    @Column
    private String avatarUrl;
    
    @Column(length = 500)
    private String description;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    @Column(name = "failed_login_count")
    private Integer failedLoginCount = 0;
    
    @Column(name = "password_expire_at")
    private LocalDateTime passwordExpireAt;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // 用户与设备的关联（一个用户可以管理多个设备）
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeviceEntity> devices = new ArrayList<>();
    
    // 用户创建的报警规则
    @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AlarmRuleEntity> createdAlarmRules = new ArrayList<>();
    
    // 处理的报警记录
    @OneToMany(mappedBy = "processedByUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AlarmRecordEntity> processedAlarms = new ArrayList<>();
    
    // 构造函数
    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = "active";
    }
    
    // 检查用户是否激活
    public boolean isActive() {
        return "active".equals(status);
    }
    
    // 检查用户是否是管理员
    public boolean isAdmin() {
        return role == UserRole.ADMIN || role == UserRole.SUPER_ADMIN;
    }
    
    // 检查用户是否可以管理设备
    public boolean canManageDevices() {
        return isAdmin() || role == UserRole.OPERATOR;
    }
}