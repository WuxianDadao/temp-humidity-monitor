package com.iot.temphumidity.entity.postgresql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 报警配置实体类
 */
@Entity
@Table(name = "alarm_config", indexes = {
    @Index(name = "idx_alarm_config_sensor_id", columnList = "sensor_id"),
    @Index(name = "idx_alarm_config_gateway_id", columnList = "gateway_id"),
    @Index(name = "idx_alarm_config_device_id", columnList = "device_id"),
    @Index(name = "idx_alarm_config_alarm_type", columnList = "alarm_type"),
    @Index(name = "idx_alarm_config_severity", columnList = "severity"),
    @Index(name = "idx_alarm_config_enabled", columnList = "enabled"),
    @Index(name = "idx_alarm_config_created_at", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AlarmConfigEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 报警配置名称
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    /**
     * 报警配置描述
     */
    @Column(name = "description", length = 500)
    private String description;
    
    /**
     * 传感器ID（可以为空，表示对所有传感器生效）
     */
    @Column(name = "sensor_id", length = 50)
    private String sensorId;
    
    /**
     * 网关ID（可以为空，表示对所有网关生效）
     */
    @Column(name = "gateway_id")
    private Long gatewayId;
    
    /**
     * 设备ID（可以为空，表示对所有设备生效）
     */
    @Column(name = "device_id")
    private Long deviceId;
    
    /**
     * 报警类型
     */
    @Column(name = "alarm_type", length = 50, nullable = false)
    private String alarmType;
    
    /**
     * 报警严重程度
     */
    @Column(name = "severity", length = 20, nullable = false)
    private String severity;
    
    /**
     * 阈值最小值
     */
    @Column(name = "threshold_min")
    private Double thresholdMin;
    
    /**
     * 阈值最大值
     */
    @Column(name = "threshold_max")
    private Double thresholdMax;
    
    /**
     * 触发条件（如持续时间、连续次数等）
     */
    @Column(name = "trigger_condition", length = 200)
    private String triggerCondition;
    
    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    
    /**
     * 报警动作（如通知方式、执行脚本等）
     */
    @Column(name = "actions", length = 1000)
    private String actions;
    
    /**
     * 静默时间（秒），0表示不静默
     */
    @Column(name = "silent_period")
    private Integer silentPeriod;
    
    /**
     * 最后触发时间
     */
    @Column(name = "last_trigger_time")
    private LocalDateTime lastTriggerTime;
    
    /**
     * 触发次数统计
     */
    @Column(name = "trigger_count")
    private Integer triggerCount;
    
    /**
     * 最大触发次数（0表示无限制）
     */
    @Column(name = "max_trigger_count")
    private Integer maxTriggerCount;
    
    /**
     * 是否已过期
     */
    @Column(name = "expired")
    private Boolean expired;
    
    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    
    /**
     * 优先级（数值越小优先级越高）
     */
    @Column(name = "priority")
    private Integer priority;
    
    /**
     * 自定义字段（JSON格式）
     */
    @Column(name = "custom_fields", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String customFields;
    
    /**
     * 版本号（用于乐观锁）
     */
    @Version
    @Column(name = "version", nullable = false)
    private Integer version = 1;
    
    /**
     * 创建者ID
     */
    @Column(name = "created_by")
    private Long createdBy;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 检查是否匹配传感器
     */
    public boolean matchesSensor(String sensorId, Long gatewayId, Long deviceId) {
        // 如果配置指定了具体的传感器ID，则必须完全匹配
        if (this.sensorId != null && !this.sensorId.equals(sensorId)) {
            return false;
        }
        
        // 如果配置指定了具体的网关ID，则必须匹配
        if (this.gatewayId != null && !this.gatewayId.equals(gatewayId)) {
            return false;
        }
        
        // 如果配置指定了具体的设备ID，则必须匹配
        if (this.deviceId != null && !this.deviceId.equals(deviceId)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 检查是否应该触发报警
     */
    public boolean shouldTrigger(Double value) {
        if (value == null || !Boolean.TRUE.equals(enabled)) {
            return false;
        }
        
        // 检查阈值条件
        boolean minCondition = thresholdMin == null || value >= thresholdMin;
        boolean maxCondition = thresholdMax == null || value <= thresholdMax;
        
        return minCondition && maxCondition;
    }
    
    /**
     * 检查是否在静默期
     */
    public boolean isInSilentPeriod() {
        if (silentPeriod == null || silentPeriod <= 0) {
            return false;
        }
        
        if (lastTriggerTime == null) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime silentEnd = lastTriggerTime.plusSeconds(silentPeriod);
        
        return now.isBefore(silentEnd);
    }
    
    /**
     * 检查是否已超过最大触发次数
     */
    public boolean hasExceededMaxTriggerCount() {
        if (maxTriggerCount == null || maxTriggerCount <= 0) {
            return false;
        }
        
        if (triggerCount == null) {
            return false;
        }
        
        return triggerCount >= maxTriggerCount;
    }
    
    /**
     * 检查是否已过期
     */
    public boolean isExpired() {
        if (Boolean.TRUE.equals(expired)) {
            return true;
        }
        
        if (expireTime != null) {
            return LocalDateTime.now().isAfter(expireTime);
        }
        
        return false;
    }
    
    /**
     * 增加触发次数
     */
    public void incrementTriggerCount() {
        if (triggerCount == null) {
            triggerCount = 1;
        } else {
            triggerCount++;
        }
        lastTriggerTime = LocalDateTime.now();
    }
    
    /**
     * 获取报警范围描述
     */
    public String getRangeDescription() {
        if (sensorId != null) {
            return "传感器: " + sensorId;
        } else if (gatewayId != null) {
            return "网关: " + gatewayId;
        } else if (deviceId != null) {
            return "设备: " + deviceId;
        } else {
            return "全局";
        }
    }
    
    /**
     * 获取阈值描述
     */
    public String getThresholdDescription() {
        if (thresholdMin != null && thresholdMax != null) {
            return String.format("%.1f ~ %.1f", thresholdMin, thresholdMax);
        } else if (thresholdMin != null) {
            return String.format("≥ %.1f", thresholdMin);
        } else if (thresholdMax != null) {
            return String.format("≤ %.1f", thresholdMax);
        } else {
            return "无阈值";
        }
    }
}