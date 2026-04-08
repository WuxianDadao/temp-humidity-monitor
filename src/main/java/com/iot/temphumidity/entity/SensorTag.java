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
 * 传感器标签实体类
 * 对应PostgreSQL中的sensor_tags表
 */
@Entity
@Table(name = "sensor_tags", 
       indexes = {
           @Index(name = "idx_sensor_tags_sn", columnList = "serial_number"),
           @Index(name = "idx_sensor_tags_type", columnList = "sensor_type"),
           @Index(name = "idx_sensor_tags_status", columnList = "status"),
           @Index(name = "idx_sensor_tags_created_at", columnList = "created_at")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_sensor_tags_sn", columnNames = "serial_number")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class SensorTag {
    
    @Column(name = "device_id")
    private Long deviceId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "serial_number", nullable = false, length = 32)
    private String serialNumber;
    
    @Column(name = "tag_code", length = 32)
    private String tagCode;
    
    @Column(name = "tag_name", length = 128)
    private String tagName;
    
    @Column(name = "sensor_type", length = 32)
    private String sensorType;
    
    @Column(name = "model", length = 64)
    private String model;
    
    @Column(name = "manufacturer", length = 64)
    private String manufacturer;
    
    @Column(name = "firmware_version", length = 32)
    private String firmwareVersion;
    
    @Column(name = "hardware_version", length = 32)
    private String hardwareVersion;
    
    @Column(name = "measurement_range_temp_min")
    private Double measurementRangeTempMin;
    
    @Column(name = "measurement_range_temp_max")
    private Double measurementRangeTempMax;
    
    @Column(name = "measurement_range_humidity_min")
    private Double measurementRangeHumidityMin;
    
    @Column(name = "measurement_range_humidity_max")
    private Double measurementRangeHumidityMax;
    
    @Column(name = "accuracy_temp")
    private Double accuracyTemp;
    
    @Column(name = "accuracy_humidity")
    private Double accuracyHumidity;
    
    @Column(name = "resolution_temp")
    private Double resolutionTemp;
    
    @Column(name = "resolution_humidity")
    private Double resolutionHumidity;
    
    @Column(name = "power_consumption")
    private Double powerConsumption;
    
    @Column(name = "battery_type", length = 32)
    private String batteryType;
    
    @Column(name = "battery_capacity")
    private Integer batteryCapacity;
    
    @Column(name = "expected_lifetime_days")
    private Integer expectedLifetimeDays;
    
    @Column(name = "communication_protocol", length = 32)
    private String communicationProtocol;
    
    @Column(name = "communication_range")
    private Double communicationRange;
    
    // 温度阈值
    @Column(name = "min_temperature")
    private Double minTemperature;
    
    @Column(name = "max_temperature")
    private Double maxTemperature;
    
    // 湿度阈值
    @Column(name = "min_humidity")
    private Double minHumidity;
    
    @Column(name = "max_humidity")
    private Double maxHumidity;
    
    // 最后记录的温度和湿度（用于实时监控）
    @Column(name = "last_temperature")
    private Double lastTemperature;
    
    @Column(name = "last_humidity")
    private Double lastHumidity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    @Builder.Default
    private TagStatus status = TagStatus.IN_STOCK;
    
    @Column(name = "manufacture_date")
    private LocalDateTime manufactureDate;
    
    @Column(name = "first_activation_date")
    private LocalDateTime firstActivationDate;
    
    @Column(name = "last_maintenance_date")
    private LocalDateTime lastMaintenanceDate;
    
    @Column(name = "next_maintenance_date")
    private LocalDateTime nextMaintenanceDate;
    
    @Column(name = "owner_id")
    private Long ownerId;
    
    @Column(name = "current_location_id")
    private Long currentLocationId;
    
    @Column(name = "qr_code_url", length = 512)
    private String qrCodeUrl;
    
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;
    
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
    
    /**
     * 标签状态枚举
     */
    public enum TagStatus {
        IN_STOCK("in_stock"),           // 库存中
        DEPLOYED("deployed"),           // 已部署
        MAINTENANCE("maintenance"),     // 维护中
        FAULTY("faulty"),               // 故障
        DECOMMISSIONED("decommissioned"), // 退役
        LOST("lost"),                   // 丢失
        DESTROYED("destroyed");         // 销毁
        
        private final String value;
        
        TagStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static TagStatus fromValue(String value) {
            for (TagStatus status : TagStatus.values()) {
                if (status.value.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的标签状态: " + value);
        }
    }
    
    /**
     * 检查标签是否可用（可以绑定）
     */
    public boolean isAvailableForBinding() {
        return status == TagStatus.IN_STOCK || status == TagStatus.MAINTENANCE;
    }
    
    /**
     * 检查标签是否已部署
     */
    public boolean isDeployed() {
        return status == TagStatus.DEPLOYED;
    }
    
    /**
     * 检查是否需要维护
     */
    public boolean needsMaintenance() {
        if (nextMaintenanceDate == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(nextMaintenanceDate);
    }
    
    /**
     * 获取传感器类型显示名称
     */
    public String getSensorTypeDisplay() {
        if ("temperature_humidity".equals(sensorType)) {
            return "温湿度传感器";
        } else if ("temperature".equals(sensorType)) {
            return "温度传感器";
        } else if ("humidity".equals(sensorType)) {
            return "湿度传感器";
        } else if ("pressure".equals(sensorType)) {
            return "压力传感器";
        }
        return sensorType;
    }
    
    /**
     * 获取测量范围描述
     */
    public String getMeasurementRangeDescription() {
        StringBuilder sb = new StringBuilder();
        if (measurementRangeTempMin != null && measurementRangeTempMax != null) {
            sb.append(String.format("温度: %.1f~%.1f°C", measurementRangeTempMin, measurementRangeTempMax));
        }
        if (measurementRangeHumidityMin != null && measurementRangeHumidityMax != null) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append(String.format("湿度: %.1f~%.1f%%", measurementRangeHumidityMin, measurementRangeHumidityMax));
        }
        return sb.toString();
    }
}