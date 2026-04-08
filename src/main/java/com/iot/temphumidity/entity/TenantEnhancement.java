package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 租户增强信息实体类
 * 对应PostgreSQL中的tenant_enhancements表
 * 存储租户的扩展配置和高级功能设置
 */
@Entity
@Table(name = "tenant_enhancements",
       indexes = {
           @Index(name = "idx_tenant_enhancements_tenant_id", columnList = "tenant_id")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantEnhancement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id", nullable = false, unique = true)
    private Tenant tenant;
    
    @Column(name = "company_size", length = 32)
    private String companySize; // 公司规模
    
    @Column(name = "industry", length = 64)
    private String industry; // 行业
    
    @Column(name = "annual_revenue", length = 32)
    private String annualRevenue; // 年收入
    
    @Column(name = "employee_count")
    private Integer employeeCount; // 员工数
    
    @Column(name = "business_license_number", length = 64)
    private String businessLicenseNumber; // 营业执照号
    
    @Column(name = "tax_identification_number", length = 64)
    private String taxIdentificationNumber; // 税号
    
    @Column(name = "business_address", length = 255)
    private String businessAddress; // 营业地址
    
    @Column(name = "primary_business_activity", length = 255)
    private String primaryBusinessActivity; // 主要业务活动
    
    @Column(name = "bank_name", length = 128)
    private String bankName; // 银行名称
    
    @Column(name = "bank_account_number", length = 64)
    private String bankAccountNumber; // 银行账号
    
    @Column(name = "payment_terms", length = 64)
    private String paymentTerms; // 付款条件
    
    @Column(name = "credit_limit")
    private Double creditLimit; // 信用额度
    
    @Column(name = "invoice_template", length = 32)
    @Builder.Default
    private String invoiceTemplate = "standard"; // 发票模板
    
    @Column(name = "invoice_due_days")
    @Builder.Default
    private Integer invoiceDueDays = 30; // 发票到期天数
    
    @Column(name = "support_level", length = 32)
    @Builder.Default
    private String supportLevel = "standard"; // 支持级别
    
    @Column(name = "sla_agreement", length = 500)
    private String slaAgreement; // SLA协议
    
    @Column(name = "data_protection_policy", length = 500)
    private String dataProtectionPolicy; // 数据保护政策
    
    @Column(name = "security_policy", length = 500)
    private String securityPolicy; // 安全政策
    
    @Column(name = "compliance_policy", length = 500)
    private String compliancePolicy; // 合规政策
    
    @Column(name = "onboarding_process", length = 500)
    private String onboardingProcess; // 入职流程
    
    @Column(name = "custom_reports_enabled")
    @Builder.Default
    private Boolean customReportsEnabled = true; // 自定义报告启用
    
    @Column(name = "analytics_dashboard_enabled")
    @Builder.Default
    private Boolean analyticsDashboardEnabled = true; // 分析仪表板启用
    
    @Column(name = "api_access_level", length = 32)
    @Builder.Default
    private String apiAccessLevel = "standard"; // API访问级别
    
    @Column(name = "data_export_enabled")
    @Builder.Default
    private Boolean dataExportEnabled = true; // 数据导出启用
    
    @Column(name = "notification_channels", length = 255)
    @Builder.Default
    private String notificationChannels = "email,web"; // 通知渠道
    
    @Column(name = "custom_branding_enabled")
    @Builder.Default
    private Boolean customBrandingEnabled = false; // 自定义品牌启用
    
    @Column(name = "whitelabel_enabled")
    @Builder.Default
    private Boolean whitelabelEnabled = false; // 白标启用
    
    @Column(name = "multi_tenant_enabled")
    @Builder.Default
    private Boolean multiTenantEnabled = true; // 多租户启用
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 检查是否有有效的SLA协议
     */
    public boolean hasValidSla() {
        return slaAgreement != null && !slaAgreement.isEmpty();
    }
    
    /**
     * 检查是否支持高级API访问
     */
    public boolean hasAdvancedApiAccess() {
        return "advanced".equalsIgnoreCase(apiAccessLevel) || 
               "enterprise".equalsIgnoreCase(apiAccessLevel);
    }
    
    /**
     * 获取支持的通知渠道列表
     */
    public String[] getNotificationChannelsList() {
        if (notificationChannels == null || notificationChannels.isEmpty()) {
            return new String[0];
        }
        return notificationChannels.split(",");
    }
    
    /**
     * 检查是否支持特定的通知渠道
     */
    public boolean supportsNotificationChannel(String channel) {
        String[] channels = getNotificationChannelsList();
        for (String c : channels) {
            if (c.trim().equalsIgnoreCase(channel)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 添加通知渠道
     */
    public void addNotificationChannel(String channel) {
        if (!supportsNotificationChannel(channel)) {
            if (notificationChannels == null || notificationChannels.isEmpty()) {
                notificationChannels = channel;
            } else {
                notificationChannels = notificationChannels + "," + channel;
            }
        }
    }
    
    /**
     * 移除通知渠道
     */
    public void removeNotificationChannel(String channel) {
        if (notificationChannels != null && !notificationChannels.isEmpty()) {
            String[] channels = notificationChannels.split(",");
            StringBuilder newChannels = new StringBuilder();
            for (String c : channels) {
                if (!c.trim().equalsIgnoreCase(channel)) {
                    if (!newChannels.isEmpty()) {
                        newChannels.append(",");
                    }
                    newChannels.append(c.trim());
                }
            }
            notificationChannels = newChannels.toString();
        }
    }
}