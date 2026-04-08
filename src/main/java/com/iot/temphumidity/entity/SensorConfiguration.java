package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * 传感器配置实体类
 * 对应PostgreSQL中的sensor_configurations表
 */
@Entity
@Table(name = "sensor_configurations",
       indexes = {
           @Index(name = "idx_sensor_configurations_sensor", columnList = "sensor_id"),
           @Index(name = "idx_sensor_configurations_type", columnList = "config_type"),
           @Index(name = "idx_sensor_configurations_version", columnList = "version"),
           @Index(name = "idx_sensor_configurations_active", columnList = "is_active")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SensorConfiguration extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sensor_id", nullable = false)
    private Long sensorId;  // 传感器ID
    
    @Column(name = "config_type", nullable = false, length = 32)
    private String configType;  // 配置类型
    
    @Column(name = "version", nullable = false, length = 32)
    private String version;  // 配置版本
    
    @Column(name = "config_data", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String configData;  // 配置数据 (JSON)
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // 配置描述
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;  // 是否生效
    
    @Column(name = "applied_at")
    private LocalDateTime appliedAt;  // 生效时间
    
    @Column(name = "applied_by")
    private Long appliedBy;  // 生效操作者
    
    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;  // 生效开始时间
    
    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;  // 生效结束时间
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;  // 备注
    
    @Column(name = "created_by")
    private Long createdBy;  // 创建者
    
    @Column(name = "updated_by")
    private Long updatedBy;  // 更新者
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 配置类型枚举
    public enum ConfigType {
        SAMPLING("sampling"),            // 采样配置
        COMMUNICATION("communication"),   // 通信配置
        CALIBRATION("calibration"),       // 校准配置
        ALARM("alarm"),                   // 报警配置
        DISPLAY("display"),               // 显示配置
        ADVANCED("advanced");             // 高级配置
        
        private final String value;
        
        ConfigType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 检查配置是否在当前时间生效
    public boolean isCurrentlyEffective() {
        if (!Boolean.TRUE.equals(isActive)) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (effectiveFrom != null && now.isBefore(effectiveFrom)) {
            return false;
        }
        if (effectiveTo != null && now.isAfter(effectiveTo)) {
            return false;
        }
        
        return true;
    }
    
    // 激活配置
    public void activate(Long activatorId) {
        this.isActive = true;
        this.appliedAt = LocalDateTime.now();
        this.appliedBy = activatorId;
    }
    
    // 停用配置
    public void deactivate() {
        this.isActive = false;
    }
    
    // 设置生效时间段
    public void setEffectivePeriod(LocalDateTime from, LocalDateTime to) {
        this.effectiveFrom = from;
        this.effectiveTo = to;
    }
    
    // 检查配置是否有冲突的时间段
    public boolean hasTimeConflict(LocalDateTime otherFrom, LocalDateTime otherTo) {
        if (this.effectiveFrom == null || this.effectiveTo == null) {
            return false; // 没有设置时间段的配置不会冲突
        }
        
        // 检查时间段是否重叠
        return !(this.effectiveTo.isBefore(otherFrom) || this.effectiveFrom.isAfter(otherTo));
    }
    
    // 预持久化回调
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (effectiveFrom == null) {
            effectiveFrom = now;
        }
        updatedAt = now;
    }
    
    // 预更新回调
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}