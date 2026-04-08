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
 * 设备标签实体类
 * 对应PostgreSQL中的device_tags表
 */
@Entity
@Table(name = "device_tags", 
       indexes = {
           @Index(name = "idx_device_tags_device_id", columnList = "device_id"),
           @Index(name = "idx_device_tags_tag_id", columnList = "tag_id"),
           @Index(name = "idx_device_tags_unique", columnList = "device_id, tag_id", unique = true)
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class DeviceTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false, foreignKey = @ForeignKey(name = "fk_device_tags_device_id"))
    private Device device;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false, foreignKey = @ForeignKey(name = "fk_device_tags_tag_id"))
    private SensorTag tag;
    
    @Column(name = "binding_type", length = 32)
    @Builder.Default
    private String bindingType = "manual";
    
    @Column(name = "binding_time")
    private LocalDateTime bindingTime;
    
    @Column(name = "unbinding_time")
    private LocalDateTime unbindingTime;
    
    @Column(name = "binding_user_id")
    private Long bindingUserId;
    
    @Column(name = "unbinding_user_id")
    private Long unbindingUserId;
    
    @Column(name = "channel", length = 16)
    @Builder.Default
    private String channel = "ch0";
    
    @Column(name = "sampling_interval")
    @Builder.Default
    private Integer samplingInterval = 60;
    
    @Column(name = "reporting_interval")
    @Builder.Default
    private Integer reportingInterval = 300;
    
    @Column(name = "calibration_offset_temp")
    @Builder.Default
    private Double calibrationOffsetTemp = 0.0;
    
    @Column(name = "calibration_offset_humidity")
    @Builder.Default
    private Double calibrationOffsetHumidity = 0.0;
    
    @Column(name = "last_calibration_at")
    private LocalDateTime lastCalibrationAt;
    
    @Column(name = "calibration_by")
    private Long calibrationBy;
    
    @Column(name = "threshold_min_temp")
    private Double thresholdMinTemp;
    
    @Column(name = "threshold_max_temp")
    private Double thresholdMaxTemp;
    
    @Column(name = "threshold_min_humidity")
    private Double thresholdMinHumidity;
    
    @Column(name = "threshold_max_humidity")
    private Double thresholdMaxHumidity;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    /**
     * 检查标签是否处于绑定状态
     */
    public boolean isBound() {
        return bindingTime != null && unbindingTime == null && isActive;
    }
    
    /**
     * 绑定标签
     */
    public void bind(Long userId) {
        this.bindingTime = LocalDateTime.now();
        this.bindingUserId = userId;
        this.unbindingTime = null;
        this.unbindingUserId = null;
        this.isActive = true;
    }
    
    /**
     * 解绑标签
     */
    public void unbind(Long userId) {
        this.unbindingTime = LocalDateTime.now();
        this.unbindingUserId = userId;
        this.isActive = false;
    }
    
    /**
     * 检查温度是否在阈值范围内
     */
    public boolean isTemperatureInRange(Double temperature) {
        if (temperature == null) return true;
        boolean withinMin = thresholdMinTemp == null || temperature >= thresholdMinTemp;
        boolean withinMax = thresholdMaxTemp == null || temperature <= thresholdMaxTemp;
        return withinMin && withinMax;
    }
    
    /**
     * 检查湿度是否在阈值范围内
     */
    public boolean isHumidityInRange(Double humidity) {
        if (humidity == null) return true;
        boolean withinMin = thresholdMinHumidity == null || humidity >= thresholdMinHumidity;
        boolean withinMax = thresholdMaxHumidity == null || humidity <= thresholdMaxHumidity;
        return withinMin && withinMax;
    }
    
    /**
     * 获取校准后的温度值
     */
    public Double getCalibratedTemperature(Double rawTemperature) {
        if (rawTemperature == null) return null;
        return rawTemperature + calibrationOffsetTemp;
    }
    
    /**
     * 获取校准后的湿度值
     */
    public Double getCalibratedHumidity(Double rawHumidity) {
        if (rawHumidity == null) return null;
        return rawHumidity + calibrationOffsetHumidity;
    }
}