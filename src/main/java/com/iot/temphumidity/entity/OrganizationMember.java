package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 组织成员关系实体类
 * 记录用户与组织的关联关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "organization_members", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"organization_id", "user_id"})
       })
public class OrganizationMember extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "role_in_org", nullable = false, length = 32)
    private String roleInOrg;
    
    @Column(name = "title", length = 128)
    private String title;
    
    @Column(name = "department", length = 128)
    private String department;
    
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
    
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;
    
    @Column(name = "invitation_code", length = 64)
    private String invitationCode;
    
    @Column(name = "invitation_status", length = 16)
    private String invitationStatus;
    
    @Column(name = "invitation_expire_time")
    private LocalDateTime invitationExpireTime;
    
    @Column(name = "invitation_sent_time")
    private LocalDateTime invitationSentTime;
    
    @Column(name = "invitation_accepted_time")
    private LocalDateTime invitationAcceptedTime;
    
    @Column(name = "join_time")
    private LocalDateTime joinTime;
    
    @Column(name = "leave_time")
    private LocalDateTime leaveTime;
    
    @Column(name = "leave_reason", length = 512)
    private String leaveReason;
    
    @Column(name = "permissions", columnDefinition = "JSONB")
    private String permissions;
    
    @Column(name = "data_scope", columnDefinition = "JSONB")
    private String dataScope;
    
    @Column(name = "approval_required", nullable = false)
    private Boolean approvalRequired = false;
    
    @Column(name = "approval_status", length = 16)
    private String approvalStatus;
    
    @Column(name = "approval_notes", length = 512)
    private String approvalNotes;
    
    @Column(name = "approval_by", length = 64)
    private String approvalBy;
    
    @Column(name = "approval_time")
    private LocalDateTime approvalTime;
    
    @Column(name = "access_level", nullable = false, length = 16)
    private String accessLevel = "member";
    
    @Column(name = "notify_settings", columnDefinition = "JSONB")
    private String notifySettings;
    
    @Column(name = "tags", length = 512)
    private String tags;
    
    @Column(name = "status", nullable = false, length = 16)
    private String status = "active";
    
    @Column(name = "remark", length = 512)
    private String remark;
    
    // 组织角色常量
    public static final String ROLE_OWNER = "owner";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_MANAGER = "manager";
    public static final String ROLE_MEMBER = "member";
    public static final String ROLE_GUEST = "guest";
    public static final String ROLE_VIEWER = "viewer";
    public static final String ROLE_OPERATOR = "operator";
    public static final String ROLE_AUDITOR = "auditor";
    
    // 邀请状态常量
    public static final String INVITATION_PENDING = "pending";
    public static final String INVITATION_SENT = "sent";
    public static final String INVITATION_ACCEPTED = "accepted";
    public static final String INVITATION_EXPIRED = "expired";
    public static final String INVITATION_REVOKED = "revoked";
    public static final String INVITATION_DECLINED = "declined";
    
    // 审批状态常量
    public static final String APPROVAL_PENDING = "pending";
    public static final String APPROVAL_APPROVED = "approved";
    public static final String APPROVAL_REJECTED = "rejected";
    public static final String APPROVAL_CANCELLED = "cancelled";
    
    // 访问级别常量
    public static final String ACCESS_OWNER = "owner";
    public static final String ACCESS_ADMIN = "admin";
    public static final String ACCESS_MANAGER = "manager";
    public static final String ACCESS_MEMBER = "member";
    public static final String ACCESS_GUEST = "guest";
    public static final String ACCESS_READONLY = "readonly";
    
    // 状态常量
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_INACTIVE = "inactive";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_SUSPENDED = "suspended";
    public static final String STATUS_TERMINATED = "terminated";
    public static final String STATUS_LEFT = "left";
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (roleInOrg == null) {
            roleInOrg = ROLE_MEMBER;
        }
        if (isDefault == null) {
            isDefault = false;
        }
        if (isPrimary == null) {
            isPrimary = false;
        }
        if (approvalRequired == null) {
            approvalRequired = false;
        }
        if (accessLevel == null) {
            accessLevel = ACCESS_MEMBER;
        }
        if (status == null) {
            status = STATUS_ACTIVE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
    }
    
    /**
     * 判断是否拥有管理员权限
     */
    public boolean isAdministrator() {
        return ROLE_OWNER.equals(roleInOrg) || 
               ROLE_ADMIN.equals(roleInOrg) || 
               ACCESS_OWNER.equals(accessLevel) || 
               ACCESS_ADMIN.equals(accessLevel);
    }
    
    /**
     * 判断是否活跃成员
     */
    public boolean isActive() {
        return STATUS_ACTIVE.equals(status) || 
               (STATUS_PENDING.equals(status) && 
                invitationStatus != null && INVITATION_ACCEPTED.equals(invitationStatus));
    }
}