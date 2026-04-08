package com.iot.temphumidity.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SoftDelete;
import com.iot.temphumidity.enums.DeviceStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备实体类
 * 对应PostgreSQL中的devices表
 * 设备通过网关与系统通信，一个设备可以绑定多个传感器标签
 */
@Entity
@Table(name = "devices",
       indexes = {
           @Index(name = "idx_devices_iccid", columnList = "iccid"),
           @Index(name = "idx_devices_imei", columnList = "imei"),
           @Index(name = "idx_devices_gateway", columnList = "gateway_id"),
           @Index(name = "idx_devices_status", columnList = "status"),
           @Index(name = "idx_devices_created_at", columnList = "created_at")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_devices_iccid", columnNames = "iccid"),
           @UniqueConstraint(name = "uk_devices_imei", columnNames = "imei")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
@EqualsAndHashCode(callSuper = false)
public class Device {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "device_id", length = 36)
    private String deviceId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", referencedColumnName = "gateway_id", nullable = false)
    private Gateway gateway;
    
    @Column(name = "iccid", nullable = false, length = 20)
    private String iccid;
    
    @Column(name = "imei", length = 15)
    private String imei;
    
    @Column(name = "device_name", nullable = false, length = 128)
    private String deviceName;
    
    @Column(name = "device_type", nullable = false, length = 32)
    private String deviceType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    @Builder.Default
    private DeviceStatus status = DeviceStatus.UNUSED;
    
    @Column(name = "location", length = 255)
    private String location;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "manufacturer", length = 64)
    private String manufacturer;
    
    @Column(name = "model", length = 64)
    private String model;
    
    @Column(name = "organization_id")
    private Long organizationId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "serial_number", length = 32)
    private String serialNumber;
    
    @Column(name = "firmware_version", length = 32)
    private String firmwareVersion;
    
    @Column(name = "hardware_version", length = 32)
    private String hardwareVersion;
    
    @Column(name = "network_type", length = 16)
    private String networkType;
    
    @Column(name = "signal_strength")
    private Integer signalStrength;
    
    @Column(name = "battery_level")
    private Integer batteryLevel;
    
    @Column(name = "battery_voltage")
    private Double batteryVoltage;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "humidity")
    private Double humidity;
    
    @Column(name = "last_data_received")
    private LocalDateTime lastDataReceived;
    
    @Column(name = "last_config_update")
    private LocalDateTime lastConfigUpdate;
    
    @Column(name = "data_interval_seconds")
    @Builder.Default
    private Integer dataIntervalSeconds = 300; // 默认5分钟
    
    @Column(name = "heartbeat_interval_seconds")
    @Builder.Default
    private Integer heartbeatIntervalSeconds = 600; // 默认10分钟
    
    @Column(name = "max_retry_count")
    @Builder.Default
    private Integer maxRetryCount = 3;
    
    @Column(name = "retry_interval_seconds")
    @Builder.Default
    private Integer retryIntervalSeconds = 30;
    
    @Column(name = "mqtt_client_id", length = 64)
    private String mqttClientId;
    
    @Column(name = "mqtt_topic_prefix", length = 128)
    private String mqttTopicPrefix;
    
    @Column(name = "report_interval")
    @Builder.Default
    private Integer reportInterval = 300; // 默认5分钟
    
    @Column(name = "alarm_enabled")
    @Builder.Default
    private Boolean alarmEnabled = true;
    
    @Column(name = "alarm_config", columnDefinition = "JSONB")
    private String alarmConfig;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;

    @Column(name = "timezone", length = 32)
    private String timezone;
    
    @Column(name = "remark", length = 500)
    private String remark;
    
    @Column(name = "tags", length = 255)
    private String tags;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SensorTag> sensorTags = new ArrayList<>();
    
    /**
     * 检查设备是否在线
     */
    public boolean isOnline() {
        if (lastDataReceived == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeout = lastDataReceived.plusSeconds(heartbeatIntervalSeconds * 2);
        return now.isBefore(timeout);
    }
    
    /**
     * 检查设备是否需要维护
     */
    public boolean needsMaintenance() {
        return status == DeviceStatus.MAINTENANCE;
    }
    
    /**
     * 获取设备位置坐标
     */
    public String getLocationCoordinates() {
        if (latitude != null && longitude != null) {
            return String.format("%.6f,%.6f", latitude, longitude);
        }
        return location;
    }
    
    /**
     * 获取设备状态显示名称
     */
    public String getStatusDisplay() {
        if (status == null) return "未知";
        return status.getDescription();
    }
    
    /**
     * 获取设备类型显示名称
     */
    public String getDeviceTypeDisplay() {
        if ("temperature_humidity_gateway".equals(deviceType)) {
            return "温湿度网关";
        } else if ("temperature_sensor".equals(deviceType)) {
            return "温度传感器";
        } else if ("humidity_sensor".equals(deviceType)) {
            return "湿度传感器";
        } else if ("gateway".equals(deviceType)) {
            return "网关设备";
        } else if ("sensor".equals(deviceType)) {
            return "传感器设备";
        }
        return deviceType;
    }
    
    /**
     * 获取传感器标签数量
     */
    public int getSensorTagCount() {
        return sensorTags != null ? sensorTags.size() : 0;
    }
    
    /**
     * 获取ID（兼容性方法，返回deviceId）
     */
    public String getId() {
        return deviceId;
    }
    
    /**
     * 获取设备名称
     */
    public String getName() {
        return deviceName;
    }
    
    /**
     * 设置设备名称
     */
    public void setName(String name) {
        this.deviceName = name;
    }
}
