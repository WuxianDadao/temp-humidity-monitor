package com.iot.temphumidity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 设备网关实体类
 * 表示4G网关与传感器的绑定关系
 */
@Entity
@Table(name = "device_gateway", 
       indexes = {
           @Index(name = "idx_device_id", columnList = "deviceId"),
           @Index(name = "idx_sensor_id", columnList = "sensorId"),
           @Index(name = "idx_online_status", columnList = "isOnline")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGateway {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 设备ID (4G网关的唯一标识，通常是ICCID)
     */
    @Column(name = "device_id", nullable = false, length = 50, unique = true)
    private String deviceId;
    
    /**
     * 传感器ID (温湿度标签的唯一标识)
     */
    @Column(name = "sensor_id", nullable = false, length = 50, unique = true)
    private String sensorId;
    
    /**
     * 设备名称
     */
    @Column(name = "device_name", length = 100)
    private String deviceName;
    
    /**
     * 传感器名称
     */
    @Column(name = "sensor_name", length = 100)
    private String sensorName;
    
    /**
     * 设备位置
     */
    @Column(name = "location", length = 200)
    private String location;
    
    /**
     * 设备类型
     */
    @Column(name = "device_type", length = 50)
    private String deviceType;
    
    /**
     * 传感器类型
     */
    @Column(name = "sensor_type", length = 50)
    private String sensorType;
    
    /**
     * 是否在线
     */
    @Column(name = "is_online")
    private Boolean isOnline = false;
    
    /**
     * 最后心跳时间
     */
    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;
    
    /**
     * 最后上报时间
     */
    @Column(name = "last_report_time")
    private LocalDateTime lastReportTime;
    
    /**
     * 信号强度
     */
    @Column(name = "signal_strength")
    private Integer signalStrength;
    
    /**
     * 电池电量 (百分比)
     */
    @Column(name = "battery_level")
    private Integer batteryLevel;
    
    /**
     * 固件版本
     */
    @Column(name = "firmware_version", length = 50)
    private String firmwareVersion;
    
    /**
     * 设备配置 (JSON格式)
     */
    @Column(name = "device_config", columnDefinition = "TEXT")
    private String deviceConfig;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 备注
     */
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    /**
     * 是否启用
     */
    @Column(name = "is_enabled")
    private Boolean isEnabled = true;
    
    /**
     * 所属用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 所属组织ID
     */
    @Column(name = "organization_id")
    private Long organizationId;
    
    /**
     * 数据采集间隔 (秒)
     */
    @Column(name = "collection_interval")
    private Integer collectionInterval = 60;
    
    /**
     * 数据上报间隔 (秒)
     */
    @Column(name = "report_interval")
    private Integer reportInterval = 300;
    
    /**
     * 报警阈值配置 (JSON格式)
     */
    @Column(name = "alarm_threshold", columnDefinition = "TEXT")
    private String alarmThreshold;
    
    /**
     * 设备状态 (0-正常, 1-警告, 2-故障)
     */
    @Column(name = "device_status")
    private Integer deviceStatus = 0;
    
    /**
     * 传感器状态 (0-正常, 1-警告, 2-故障)
     */
    @Column(name = "sensor_status")
    private Integer sensorStatus = 0;
    
    /**
     * 网络类型 (4G, 5G, WiFi, Ethernet)
     */
    @Column(name = "network_type", length = 20)
    private String networkType;
    
    /**
     * IP地址
     */
    @Column(name = "ip_address", length = 50)
    private String ipAddress;
    
    /**
     * MAC地址
     */
    @Column(name = "mac_address", length = 20)
    private String macAddress;
    
    /**
     * 设备序列号
     */
    @Column(name = "serial_number", length = 100)
    private String serialNumber;
    
    /**
     * 生产厂商
     */
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
    
    /**
     * 设备型号
     */
    @Column(name = "model", length = 100)
    private String model;
    
    /**
     * 安装日期
     */
    @Column(name = "installation_date")
    private LocalDateTime installationDate;
    
    /**
     * 维护周期 (天)
     */
    @Column(name = "maintenance_cycle")
    private Integer maintenanceCycle;
    
    /**
     * 下次维护时间
     */
    @Column(name = "next_maintenance_date")
    private LocalDateTime nextMaintenanceDate;
    
    /**
     * 地理坐标 (经度)
     */
    @Column(name = "longitude")
    private Double longitude;
    
    /**
     * 地理坐标 (纬度)
     */
    @Column(name = "latitude")
    private Double latitude;
    
    /**
     * 海拔高度
     */
    @Column(name = "altitude")
    private Double altitude;
    
    /**
     * 数据存储时间 (天)
     */
    @Column(name = "data_retention_days")
    private Integer dataRetentionDays = 365;
    
    /**
     * 数据压缩方式
     */
    @Column(name = "data_compression", length = 50)
    private String dataCompression;
    
    /**
     * 数据加密方式
     */
    @Column(name = "data_encryption", length = 50)
    private String dataEncryption;
    
    /**
     * 自定义字段1
     */
    @Column(name = "custom_field1", length = 200)
    private String customField1;
    
    /**
     * 自定义字段2
     */
    @Column(name = "custom_field2", length = 200)
    private String customField2;
    
    /**
     * 自定义字段3
     */
    @Column(name = "custom_field3", length = 200)
    private String customField3;
    
    /**
     * 实体创建前的预处理
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        
        // 设置默认值
        if (this.isOnline == null) {
            this.isOnline = false;
        }
        if (this.isEnabled == null) {
            this.isEnabled = true;
        }
        if (this.deviceStatus == null) {
            this.deviceStatus = 0;
        }
        if (this.sensorStatus == null) {
            this.sensorStatus = 0;
        }
        if (this.collectionInterval == null) {
            this.collectionInterval = 60;
        }
        if (this.reportInterval == null) {
            this.reportInterval = 300;
        }
        if (this.dataRetentionDays == null) {
            this.dataRetentionDays = 365;
        }
    }
    
    /**
     * 实体更新前的预处理
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * 检查设备是否需要维护
     */
    public boolean needsMaintenance() {
        if (nextMaintenanceDate == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(nextMaintenanceDate);
    }
    
    /**
     * 检查设备是否离线超时 (超过5分钟无心跳)
     */
    public boolean isOfflineTimeout() {
        if (isOnline != null && isOnline) {
            return false;
        }
        if (lastHeartbeat == null) {
            return true;
        }
        return lastHeartbeat.plusMinutes(5).isBefore(LocalDateTime.now());
    }
    
    /**
     * 获取设备状态描述
     */
    public String getDeviceStatusDesc() {
        switch (deviceStatus) {
            case 0: return "正常";
            case 1: return "警告";
            case 2: return "故障";
            default: return "未知";
        }
    }
    
    /**
     * 获取传感器状态描述
     */
    public String getSensorStatusDesc() {
        switch (sensorStatus) {
            case 0: return "正常";
            case 1: return "警告";
            case 2: return "故障";
            default: return "未知";
        }
    }
}