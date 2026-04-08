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
 * 组织实体类
 * 对应PostgreSQL中的organizations表
 */
@Entity
@Table(name = "organizations", 
       indexes = {
           @Index(name = "idx_organizations_name", columnList = "name"),
           @Index(name = "idx_organizations_code", columnList = "code"),
           @Index(name = "idx_organizations_parent_id", columnList = "parent_id"),
           @Index(name = "idx_organizations_status", columnList = "status")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_organizations_code", columnNames = "code")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class Organization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @Column(name = "code", length = 32)
    private String code;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "logo_url", length = 512)
    private String logoUrl;
    
    @Column(name = "website", length = 255)
    private String website;
    
    @Column(name = "contact_person", length = 64)
    private String contactPerson;
    
    @Column(name = "contact_email", length = 255)
    private String contactEmail;
    
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;
    
    @Column(name = "address", length = 255)
    private String address;
    
    @Column(name = "city", length = 64)
    private String city;
    
    @Column(name = "state", length = 64)
    private String state;
    
    @Column(name = "country", length = 64)
    private String country;
    
    @Column(name = "postal_code", length = 16)
    private String postalCode;
    
    @Column(name = "timezone", length = 64)
    @Builder.Default
    private String timezone = "Asia/Shanghai";
    
    @Column(name = "language", length = 16)
    @Builder.Default
    private String language = "zh-CN";
    
    @Column(name = "currency", length = 3)
    @Builder.Default
    private String currency = "CNY";
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    @Builder.Default
    private OrganizationStatus status = OrganizationStatus.ACTIVE;
    
    @Column(name = "type", length = 32)
    private String type;
    
    @Column(name = "industry", length = 64)
    private String industry;
    
    @Column(name = "size_category", length = 32)
    private String sizeCategory;
    
    @Column(name = "founding_year")
    private Integer foundingYear;
    
    @Column(name = "tax_id", length = 32)
    private String taxId;
    
    @Column(name = "registration_number", length = 32)
    private String registrationNumber;
    
    @Column(name = "max_users")
    private Integer maxUsers;
    
    @Column(name = "max_devices")
    private Integer maxDevices;
    
    @Column(name = "storage_quota_mb")
    private Long storageQuotaMb;
    
    @Column(name = "data_retention_days")
    @Builder.Default
    private Integer dataRetentionDays = 365;
    
    @Column(name = "subscription_plan", length = 32)
    private String subscriptionPlan;
    
    @Column(name = "subscription_start_date")
    private LocalDateTime subscriptionStartDate;
    
    @Column(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;
    
    @Column(name = "trial_end_date")
    private LocalDateTime trialEndDate;
    
    @Column(name = "billing_email", length = 255)
    private String billingEmail;
    
    @Column(name = "invoice_prefix", length = 16)
    private String invoicePrefix;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_organizations_parent_id"))
    private Organization parent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", foreignKey = @ForeignKey(name = "fk_organizations_created_by"))
    private User createdBy;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    /**
     * 组织状态枚举
     */
    public enum OrganizationStatus {
        ACTIVE("active"),
        SUSPENDED("suspended"),
        INACTIVE("inactive"),
        DELETED("deleted");
        
        private final String value;
        
        OrganizationStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static OrganizationStatus fromValue(String value) {
            for (OrganizationStatus status : OrganizationStatus.values()) {
                if (status.value.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的组织状态: " + value);
        }
    }
    
    /**
     * 检查组织是否活跃
     */
    public boolean isActive() {
        return status == OrganizationStatus.ACTIVE;
    }
    
    /**
     * 检查是否在试用期内
     */
    public boolean isInTrialPeriod() {
        if (trialEndDate == null) {
            return false;
        }
        return LocalDateTime.now().isBefore(trialEndDate);
    }
    
    /**
     * 检查订阅是否有效
     */
    public boolean isSubscriptionValid() {
        if (subscriptionEndDate == null) {
            return true; // 没有订阅限制
        }
        return LocalDateTime.now().isBefore(subscriptionEndDate);
    }
    
    /**
     * 获取完整地址
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (address != null && !address.isEmpty()) {
            sb.append(address);
        }
        if (city != null && !city.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(city);
        }
        if (state != null && !state.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(state);
        }
        if (country != null && !country.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(country);
        }
        return sb.toString();
    }
    
    /**
     * 获取组织层级路径
     */
    public String getOrganizationPath() {
        if (parent == null) {
            return name;
        }
        return parent.getOrganizationPath() + " / " + name;
    }
    
    /**
     * 组织状态枚举
     */
    public enum OrgStatus {
        ACTIVE("活跃"),
        INACTIVE("不活跃"),
        SUSPENDED("暂停"),
        ARCHIVED("归档");
        
        private final String description;
        
        OrgStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * 组织规模枚举
     */
    public enum OrgScale {
        MICRO("微型企业", 1, 10),
        SMALL("小型企业", 11, 50),
        MEDIUM("中型企业", 51, 250),
        LARGE("大型企业", 251, 1000),
        ENTERPRISE("企业级", 1001, Integer.MAX_VALUE);
        
        private final String description;
        private final int minEmployees;
        private final int maxEmployees;
        
        OrgScale(String description, int minEmployees, int maxEmployees) {
            this.description = description;
            this.minEmployees = minEmployees;
            this.maxEmployees = maxEmployees;
        }
        
        public String getDescription() {
            return description;
        }
        
        public int getMinEmployees() {
            return minEmployees;
        }
        
        public int getMaxEmployees() {
            return maxEmployees;
        }
        
        public boolean isInRange(int employees) {
            return employees >= minEmployees && employees <= maxEmployees;
        }
    }
    
    /**
     * 组织类型枚举
     */
    public enum OrgType {
        COMPANY("公司"),
        GOVERNMENT("政府机构"),
        NON_PROFIT("非营利组织"),
        EDUCATIONAL("教育机构"),
        HEALTHCARE("医疗机构"),
        RETAIL("零售企业"),
        MANUFACTURING("制造企业"),
        TECHNOLOGY("科技公司"),
        FINANCE("金融机构"),
        OTHER("其他");
        
        private final String description;
        
        OrgType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}