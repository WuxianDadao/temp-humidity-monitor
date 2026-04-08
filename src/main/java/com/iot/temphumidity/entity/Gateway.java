package com.iot.temphumidity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.temphumidity.enums.GatewayStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 网关实体
 * 对应PostgreSQL中的gateways表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gateways", indexes = {
    @Index(name = "idx_gateways_sn", columnList = "gateway_sn", unique = true),
    @Index(name = "idx_gateways_organization", columnList = "organization_id"),
    @Index(name = "idx_gateways_status", columnList = "status, is_active"),
    @Index(name = "idx_gateways_last_seen", columnList = "last_seen_at")
})
public class Gateway extends BaseEntity {
    
    /**
     * SIM卡ICCID (唯一设备号)
     */
    @Column(name = "gateway_sn", nullable = false, unique = true, length = 50)
    private String gatewaySn;
    
    /**
     * 网关名称
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * 描述
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * 所属组织
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", foreignKey = @ForeignKey(name = "fk_gateways_organization"))
    @JsonIgnore
    private Organization organization;
    
    /**
     * 组织ID（用于查询）
     */
    @Transient
    @JsonProperty("organizationId")
    public Long getOrganizationId() {
        return organization != null ? organization.getId() : null;
    }
    
    /**
     * 型号
     */
    @Column(name = "model", length = 50)
    private String model;
    
    /**
     * 制造商
     */
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
    
    /**
     * 固件版本
     */
    @Column(name = "firmware_version", length = 20)
    private String firmwareVersion;
    
    /**
     * 硬件版本
     */
    @Column(name = "hardware_version", length = 20)
    private String hardwareVersion;
    
    /**
     * IP地址
     */
    @Column(name = "ip_address")
    private String ipAddress;
    
    /**
     * MAC地址
     */
    @Column(name = "mac_address", length = 17)
    private String macAddress;
    
    /**
     * SIM卡号
     */
    @Column(name = "sim_card_number", length = 20)
    private String simCardNumber;
    
    /**
     * 网络运营商
     */
    @Column(name = "network_operator", length = 50)
    private String networkOperator;
    
    /**
     * 位置描述
     */
    @Column(name = "location", length = 200)
    private String location;
    
    /**
     * 位置详细描述
     */
    @Column(name = "location_description", length = 500)
    private String locationDescription;
    
    /**
     * 纬度
     */
    @Column(name = "latitude", precision = 10, scale = 8)
    private Double latitude;
    
    /**
     * 经度
     */
    @Column(name = "longitude", precision = 11, scale = 8)
    private Double longitude;
    
    /**
     * 海拔
     */
    @Column(name = "altitude", precision = 8, scale = 2)
    private Double altitude;
    
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private GatewayStatus status = GatewayStatus.OFFLINE;
    
    public GatewayStatus getStatus() {
        return status;
    }
    
    public void setStatus(GatewayStatus status) {
        this.status = status;
    }
    
    /**
     * 是否激活
     */
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    /**
     * 最后在线时间
     */
    @Column(name = "last_seen_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSeenAt;
    
    /**
     * 最后数据接收时间
     */
    @Column(name = "last_data_received_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastDataReceivedAt;
    
    /**
     * MQTT主题前缀
     */
    @Column(name = "mqtt_topic_prefix", length = 100)
    private String mqttTopicPrefix;
    
    /**
     * HTTP配置URL
     */
    @Column(name = "http_config_url", length = 255)
    private String httpConfigUrl;
    
    /**
     * 上报间隔（秒）
     */
    @Column(name = "report_interval")
    private Integer reportInterval = 60;
    
    /**
     * 激活时间
     */
    @Column(name = "activated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activatedAt;
    
    /**
     * 总设备数
     */
    @Column(name = "total_devices")
    private Integer totalDevices = 0;
    
    /**
     * 总数据点数
     */
    @Column(name = "total_data_points")
    private Long totalDataPoints = 0L;
    
    /**
     * 配置（JSON格式）
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "config", columnDefinition = "jsonb")
    private String config = "{}";
    
    /**
     * 元数据（JSON格式）
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata = "{}";
    
    /**
     * 关联的设备列表
     */
    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Device> devices = new ArrayList<>();
    
    /**
     * 设备数量（用于响应）
     */
    @Transient
    @JsonProperty("deviceCount")
    public Integer getDeviceCount() {
        return devices != null ? devices.size() : 0;
    }
    
    /**
     * 在线设备数量（用于响应）
     */
    @Transient
    @JsonProperty("onlineDeviceCount")
    public Integer getOnlineDeviceCount() {
        if (devices == null) return 0;
        return (int) devices.stream()
                .filter(device -> device.isOnline())
                .count();
    }
    
    // ========== 业务方法 ==========
    
    /**
     * 检查网关是否在线
     */
    @Transient
    @JsonProperty("isOnline")
    public Boolean getIsOnline() {
        return status == GatewayStatus.ONLINE;
    }
    
    /**
     * 检查是否需要更新状态
     */
    @Transient
    public Boolean needsStatusUpdate() {
        if (lastSeenAt == null) return true;
        LocalDateTime now = LocalDateTime.now();
        long minutesSinceLastSeen = java.time.Duration.between(lastSeenAt, now).toMinutes();
        return minutesSinceLastSeen > 5; // 5分钟无更新
    }
    
    /**
     * 检查网关是否健康
     */
    @Transient
    @JsonProperty("isHealthy")
    public Boolean getIsHealthy() {
        if (status != GatewayStatus.ONLINE) return false;
        if (lastSeenAt == null) return false;
        
        LocalDateTime now = LocalDateTime.now();
        long minutesSinceLastSeen = java.time.Duration.between(lastSeenAt, now).toMinutes();
        return minutesSinceLastSeen <= 30; // 30分钟内有心跳
    }
    
    /**
     * 获取离线时长（分钟）
     */
    @Transient
    @JsonProperty("offlineDurationMinutes")
    public Long getOfflineDurationMinutes() {
        if (status == GatewayStatus.ONLINE) return 0L;
        if (lastSeenAt == null) return null;
        
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(lastSeenAt, now).toMinutes();
    }
    
    /**
     * 获取状态描述
     */
    @Transient
    @JsonProperty("statusDescription")
    public String getStatusDescription() {
        switch (status) {
            case ONLINE:
                return "在线";
            case OFFLINE:
                return "离线";
            case MAINTENANCE:
                return "维护中";
            case ABNORMAL:
                return "异常";
            default:
                return "未知";
        }
    }
    
    /**
     * 添加设备
     */
    public void addDevice(Device device) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        devices.add(device);
        device.setGateway(this);
        totalDevices = devices.size();
    }
    
    /**
     * 移除设备
     */
    public void removeDevice(Device device) {
        if (devices != null) {
            devices.remove(device);
            device.setGateway(null);
            totalDevices = devices.size();
        }
    }
    
    /**
     * 更新最后在线时间
     */
    public void updateLastSeen() {
        this.lastSeenAt = LocalDateTime.now();
        this.status = GatewayStatus.ONLINE;
    }
    
    /**
     * 更新数据接收时间
     */
    public void updateDataReceived() {
        this.lastDataReceivedAt = LocalDateTime.now();
        this.totalDataPoints++;
    }
    
    /**
     * 检查配置是否有效
     */
    @Transient
    public boolean isConfigValid() {
        return mqttTopicPrefix != null && !mqttTopicPrefix.isEmpty() &&
               reportInterval != null && reportInterval > 0;
    }
    
    /**
     * 获取MQTT完整主题
     */
    @Transient
    public String getMqttFullTopic(String deviceId) {
        if (mqttTopicPrefix == null) {
            return "iot/" + gatewaySn + "/" + deviceId;
        }
        return mqttTopicPrefix + "/" + gatewaySn + "/" + deviceId;
    }
    
    /**
     * 获取配置URL
     */
    @Transient
    public String getConfigUrl() {
        if (httpConfigUrl != null && !httpConfigUrl.isEmpty()) {
            return httpConfigUrl;
        }
        // 默认配置URL
        return "http://" + (ipAddress != null ? ipAddress : "localhost") + ":8080/api/gateways/" + id + "/config";
    }
    
    /**
     * 转换为简单DTO
     */
    @Transient
    public GatewaySimpleDTO toSimpleDTO() {
        GatewaySimpleDTO dto = new GatewaySimpleDTO();
        dto.setId(id);
        dto.setGatewaySn(gatewaySn);
        dto.setName(name);
        dto.setStatus(status);
        dto.setIsOnline(getIsOnline());
        dto.setLocation(location);
        dto.setLocationDescription(locationDescription);
        dto.setLastSeenAt(lastSeenAt);
        dto.setDeviceCount(getDeviceCount());
        dto.setOnlineDeviceCount(getOnlineDeviceCount());
        return dto;
    }
    
    // ========== 内部DTO类 ==========
    
    @Data
    public static class GatewaySimpleDTO {
        private Long id;
        private String gatewaySn;
        private String name;
        private GatewayStatus status;
        private Boolean isOnline;
        private String location;
        private String locationDescription;
        private LocalDateTime lastSeenAt;
        private Integer deviceCount;
        private Integer onlineDeviceCount;
    }
    
}