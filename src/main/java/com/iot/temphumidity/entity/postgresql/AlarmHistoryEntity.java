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
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 报警历史实体类
 */
@Entity
@Table(name = "alarm_history", indexes = {
    @Index(name = "idx_alarm_history_sensor_id", columnList = "sensor_id"),
    @Index(name = "idx_alarm_history_gateway_id", columnList = "gateway_id"),
    @Index(name = "idx_alarm_history_device_id", columnList = "device_id"),
    @Index(name = "idx_alarm_history_alarm_config_id", columnList = "alarm_config_id"),
    @Index(name = "idx_alarm_history_alarm_type", columnList = "alarm_type"),
    @Index(name = "idx_alarm_history_severity", columnList = "severity"),
    @Index(name = "idx_alarm_history_status", columnList = "status"),
    @Index(name = "idx_alarm_history_trigger_time", columnList = "trigger_time"),
    @Index(name = "idx_alarm_history_resolve_time", columnList = "resolve_time"),
    @Index(name = "idx_alarm_history_acknowledged_time", columnList = "acknowledged_time"),
    @Index(name = "idx_alarm_history_created_at", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AlarmHistoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 传感器ID
     */
    @Column(name = "sensor_id", length = 50, nullable = false)
    private String sensorId;
    
    /**
     * 网关ID
     */
    @Column(name = "gateway_id")
    private Long gatewayId;
    
    /**
     * 网关名称
     */
    @Column(name = "gateway_name", length = 100)
    private String gatewayName;
    
    /**
     * 设备ID
     */
    @Column(name = "device_id")
    private Long deviceId;
    
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
     * 报警配置ID
     */
    @Column(name = "alarm_config_id")
    private Long alarmConfigId;
    
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
     * 报警消息
     */
    @Column(name = "message", length = 500)
    private String message;
    
    /**
     * 触发数值
     */
    @Column(name = "trigger_value")
    private Double triggerValue;
    
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
     * 触发时间
     */
    @Column(name = "trigger_time", nullable = false)
    private LocalDateTime triggerTime;
    
    /**
     * 确认时间
     */
    @Column(name = "acknowledged_time")
    private LocalDateTime acknowledgedTime;
    
    /**
     * 确认人ID
     */
    @Column(name = "acknowledged_by")
    private Long acknowledgedBy;
    
    /**
     * 确认备注
     */
    @Column(name = "acknowledged_comment", length = 500)
    private String acknowledgedComment;
    
    /**
     * 解决时间
     */
    @Column(name = "resolve_time")
    private LocalDateTime resolveTime;
    
    /**
     * 解决人ID
     */
    @Column(name = "resolved_by")
    private Long resolvedBy;
    
    /**
     * 解决方案
     */
    @Column(name = "resolution", length = 500)
    private String resolution;
    
    /**
     * 报警状态
     */
    @Column(name = "status", length = 20, nullable = false)
    private String status;
    
    /**
     * 计算持续时间（秒）
     */
    @Transient
    private Long calculatedDuration;
    
    /**
     * 自定义字段（JSON格式）
     */
    @Column(name = "custom_fields", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String customFields;
    
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
     * 计算报警状态
     */
    public String calculateStatus() {
        if (resolveTime != null) {
            return "RESOLVED";
        } else if (acknowledgedTime != null) {
            return "ACKNOWLEDGED";
        } else {
            return "TRIGGERED";
        }
    }
    
    /**
     * 计算报警持续时间
     */
    public Long calculateDuration() {
        LocalDateTime endTime = resolveTime != null ? resolveTime : LocalDateTime.now();
        if (triggerTime != null) {
            Duration duration = Duration.between(triggerTime, endTime);
            return duration.toSeconds();
        }
        return null;
    }
    
    /**
     * 判断是否可以确认
     */
    public boolean canAcknowledge() {
        return resolveTime == null && acknowledgedTime == null;
    }
    
    /**
     * 判断是否可以解决
     */
    public boolean canResolve() {
        return resolveTime == null;
    }
    
    /**
     * 判断是否已过期
     */
    public boolean isExpired(LocalDateTime cutoffTime) {
        return triggerTime != null && triggerTime.isBefore(cutoffTime);
    }
    
    /**
     * 判断是否为严重报警
     */
    public boolean isCritical() {
        return "CRITICAL".equals(severity) || "HIGH".equals(severity);
    }
    
    /**
     * 获取报警持续时间（人类可读格式）
     */
    public String getDurationHumanReadable() {
        Long seconds = calculateDuration();
        if (seconds == null) return "N/A";
        
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes, secs);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, secs);
        } else {
            return String.format("%ds", secs);
        }
    }
    
    /**
     * 获取报警来源信息
     */
    public String getSourceInfo() {
        StringBuilder sb = new StringBuilder();
        if (gatewayName != null) {
            sb.append(gatewayName);
        }
        if (deviceName != null) {
            if (sb.length() > 0) sb.append(" - ");
            sb.append(deviceName);
        }
        if (sensorName != null) {
            if (sb.length() > 0) sb.append(" - ");
            sb.append(sensorName);
        }
        return sb.length() > 0 ? sb.toString() : sensorId;
    }
    
    /**
     * 获取报警详情
     */
    public String getAlarmDetails() {
        return String.format("%s: %s (%.1f)", alarmType, message != null ? message : "报警", 
                            triggerValue != null ? triggerValue : 0.0);
    }
}