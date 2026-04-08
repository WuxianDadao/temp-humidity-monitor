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
 * 租户实体类
 * 对应PostgreSQL中的tenants表
 * 支持多租户架构，每个租户有独立的数据隔离
 */
@Entity
@Table(name = "tenants",
       indexes = {
           @Index(name = "idx_tenants_domain", columnList = "domain"),
           @Index(name = "idx_tenants_status", columnList = "status"),
           @Index(name = "idx_tenants_created_at", columnList = "created_at")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_tenants_domain", columnNames = "domain"),
           @UniqueConstraint(name = "uk_tenants_subdomain", columnNames = "subdomain")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class Tenant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", nullable = false, length = 128)
    private String name; // 租户名称
    
    @Column(name = "domain", nullable = false, length = 255)
    private String domain; // 主域名
    
    @Column(name = "subdomain", length = 64)
    private String subdomain; // 子域名
    
    @Column(name = "description", length = 500)
    private String description; // 描述
    
    @Column(name = "logo_url", length = 512)
    private String logoUrl; // Logo URL
    
    @Column(name = "favicon_url", length = 512)
    private String faviconUrl; // 网站图标URL
    
    @Column(name = "primary_color", length = 16)
    private String primaryColor; // 主色调
    
    @Column(name = "secondary_color", length = 16)
    private String secondaryColor; // 次色调
    
    @Column(name = "contact_name", length = 128)
    private String contactName; // 联系人姓名
    
    @Column(name = "contact_email", length = 255)
    private String contactEmail; // 联系人邮箱
    
    @Column(name = "contact_phone", length = 20)
    private String contactPhone; // 联系人电话
    
    @Column(name = "address", length = 255)
    private String address; // 地址
    
    @Column(name = "city", length = 64)
    private String city; // 城市
    
    @Column(name = "state", length = 64)
    private String state; // 州/省
    
    @Column(name = "country", length = 64)
    private String country; // 国家
    
    @Column(name = "postal_code", length = 20)
    private String postalCode; // 邮政编码
    
    @Column(name = "timezone", length = 64)
    @Builder.Default
    private String timezone = "Asia/Shanghai"; // 时区
    
    @Column(name = "language", length = 16)
    @Builder.Default
    private String language = "zh-CN"; // 语言
    
    @Column(name = "currency", length = 8)
    @Builder.Default
    private String currency = "CNY"; // 货币
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    @Builder.Default
    private TenantStatus status = TenantStatus.ACTIVE; // 状态
    
    @Column(name = "plan_type", length = 32)
    @Builder.Default
    private String planType = "free"; // 套餐类型
    
    @Column(name = "max_users")
    @Builder.Default
    private Integer maxUsers = 10; // 最大用户数
    
    @Column(name = "max_devices")
    @Builder.Default
    private Integer maxDevices = 100; // 最大设备数
    
    @Column(name = "max_gateways")
    @Builder.Default
    private Integer maxGateways = 10; // 最大网关数
    
    @Column(name = "data_retention_days")
    @Builder.Default
    private Integer dataRetentionDays = 365; // 数据保留天数
    
    @Column(name = "api_rate_limit")
    @Builder.Default
    private Integer apiRateLimit = 1000; // API速率限制
    
    @Column(name = "storage_limit_gb")
    @Builder.Default
    private Integer storageLimitGb = 10; // 存储限制(GB)
    
    @Column(name = "bandwidth_limit_gb")
    @Builder.Default
    private Integer bandwidthLimitGb = 50; // 带宽限制(GB)
    
    @Column(name = "ssl_enabled")
    @Builder.Default
    private Boolean sslEnabled = true; // SSL启用
    
    @Column(name = "custom_domain_enabled")
    @Builder.Default
    private Boolean customDomainEnabled = false; // 自定义域名启用
    
    @Column(name = "branding_enabled")
    @Builder.Default
    private Boolean brandingEnabled = true; // 品牌定制启用
    
    @Column(name = "sso_enabled")
    @Builder.Default
    private Boolean ssoEnabled = false; // 单点登录启用
    
    @Column(name = "audit_log_enabled")
    @Builder.Default
    private Boolean auditLogEnabled = true; // 审计日志启用
    
    @Column(name = "backup_enabled")
    @Builder.Default
    private Boolean backupEnabled = true; // 备份启用
    
    @Column(name = "monitoring_enabled")
    @Builder.Default
    private Boolean monitoringEnabled = true; // 监控启用
    
    @Column(name = "support_level", length = 32)
    @Builder.Default
    private String supportLevel = "basic"; // 支持级别
    
    @Column(name = "billing_cycle", length = 16)
    @Builder.Default
    private String billingCycle = "monthly"; // 计费周期
    
    @Column(name = "monthly_price")
    @Builder.Default
    private Double monthlyPrice = 0.0; // 月费
    
    @Column(name = "annual_price")
    @Builder.Default
    private Double annualPrice = 0.0; // 年费
    
    @Column(name = "setup_fee")
    @Builder.Default
    private Double setupFee = 0.0; // 设置费
    
    @Column(name = "trial_days")
    @Builder.Default
    private Integer trialDays = 14; // 试用天数
    
    @Column(name = "trial_ends_at")
    private LocalDateTime trialEndsAt; // 试用结束时间
    
    @Column(name = "subscription_starts_at")
    private LocalDateTime subscriptionStartsAt; // 订阅开始时间
    
    @Column(name = "subscription_ends_at")
    private LocalDateTime subscriptionEndsAt; // 订阅结束时间
    
    @Column(name = "auto_renew")
    @Builder.Default
    private Boolean autoRenew = true; // 自动续订
    
    @Column(name = "payment_method", length = 32)
    private String paymentMethod; // 支付方式
    
    @Column(name = "stripe_customer_id", length = 64)
    private String stripeCustomerId; // Stripe客户ID
    
    @Column(name = "stripe_subscription_id", length = 64)
    private String stripeSubscriptionId; // Stripe订阅ID
    
    @Column(name = "invoice_prefix", length = 16)
    @Builder.Default
    private String invoicePrefix = "TEN"; // 发票前缀
    
    @Column(name = "next_invoice_number")
    @Builder.Default
    private Integer nextInvoiceNumber = 1; // 下一个发票号
    
    @Column(name = "tax_rate")
    @Builder.Default
    private Double taxRate = 0.0; // 税率
    
    @Column(name = "tax_number", length = 64)
    private String taxNumber; // 税号
    
    @Column(name = "company_registration_number", length = 64)
    private String companyRegistrationNumber; // 公司注册号
    
    @Column(name = "vat_number", length = 64)
    private String vatNumber; // VAT号码
    
    @Column(name = "terms_accepted")
    @Builder.Default
    private Boolean termsAccepted = false; // 条款接受
    
    @Column(name = "privacy_policy_accepted")
    @Builder.Default
    private Boolean privacyPolicyAccepted = false; // 隐私政策接受
    
    @Column(name = "data_processing_agreement_accepted")
    @Builder.Default
    private Boolean dataProcessingAgreementAccepted = false; // 数据处理协议接受
    
    @Column(name = "marketing_consent")
    @Builder.Default
    private Boolean marketingConsent = false; // 营销同意
    
    @Column(name = "newsletter_subscription")
    @Builder.Default
    private Boolean newsletterSubscription = false; // 新闻订阅
    
    @Column(name = "custom_field_1", length = 255)
    private String customField1; // 自定义字段1
    
    @Column(name = "custom_field_2", length = 255)
    private String customField2; // 自定义字段2
    
    @Column(name = "custom_field_3", length = 255)
    private String customField3; // 自定义字段3
    
    @Column(name = "custom_field_4", length = 255)
    private String customField4; // 自定义字段4
    
    @Column(name = "custom_field_5", length = 255)
    private String customField5; // 自定义字段5
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    /**
     * 租户状态枚举
     */
    public enum TenantStatus {
        ACTIVE("active"),
        INACTIVE("inactive"),
        SUSPENDED("suspended"),
        CANCELLED("cancelled"),
        TRIAL("trial"),
        EXPIRED("expired");
        
        private final String value;
        
        TenantStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static TenantStatus fromValue(String value) {
            for (TenantStatus status : TenantStatus.values()) {
                if (status.value.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的租户状态: " + value);
        }
    }
    
    /**
     * 检查租户是否处于试用期
     */
    public boolean isInTrial() {
        if (trialEndsAt == null) {
            return false;
        }
        return LocalDateTime.now().isBefore(trialEndsAt) && 
               (status == TenantStatus.TRIAL || status == TenantStatus.ACTIVE);
    }
    
    /**
     * 检查租户是否活跃
     */
    public boolean isActive() {
        return status == TenantStatus.ACTIVE || 
               status == TenantStatus.TRIAL || 
               isInTrial();
    }
    
    /**
     * 检查订阅是否有效
     */
    public boolean isSubscriptionValid() {
        if (subscriptionEndsAt == null) {
            return true;
        }
        return LocalDateTime.now().isBefore(subscriptionEndsAt);
    }
    
    /**
     * 计算剩余试用天数
     */
    public Integer getRemainingTrialDays() {
        if (trialEndsAt == null) {
            return 0;
        }
        if (LocalDateTime.now().isAfter(trialEndsAt)) {
            return 0;
        }
        long days = java.time.Duration.between(LocalDateTime.now(), trialEndsAt).toDays();
        return Math.max(0, (int) days);
    }
    
    /**
     * 检查是否超过用户限制
     */
    public boolean isUserLimitExceeded(Integer currentUserCount) {
        return maxUsers > 0 && currentUserCount >= maxUsers;
    }
    
    /**
     * 检查是否超过设备限制
     */
    public boolean isDeviceLimitExceeded(Integer currentDeviceCount) {
        return maxDevices > 0 && currentDeviceCount >= maxDevices;
    }
    
    /**
     * 检查是否超过网关限制
     */
    public boolean isGatewayLimitExceeded(Integer currentGatewayCount) {
        return maxGateways > 0 && currentGatewayCount >= maxGateways;
    }
    
    /**
     * 生成下一个发票号
     */
    public String generateInvoiceNumber() {
        String number = String.format("%s-%06d", invoicePrefix, nextInvoiceNumber);
        nextInvoiceNumber++;
        return number;
    }
    
    /**
     * 获取完整的域名
     */
    public String getFullDomain() {
        if (customDomainEnabled && domain != null && !domain.isEmpty()) {
            return domain;
        }
        if (subdomain != null && !subdomain.isEmpty()) {
            return subdomain + ".iot-monitoring.com";
        }
        return "tenant-" + id + ".iot-monitoring.com";
    }
    
    /**
     * 获取网站URL
     */
    public String getWebsiteUrl() {
        String protocol = sslEnabled ? "https://" : "http://";
        return protocol + getFullDomain();
    }
}