package com.iot.temphumidity.dto.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 报警历史数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class AlarmHistoryDTO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 传感器ID
     */
    private String sensorId;
    
    /**
     * 网关ID
     */
    private Long gatewayId;
    
    /**
     * 网关名称
     */
    private String gatewayName;
    
    /**
     * 设备ID
     */
    private Long deviceId;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 传感器名称
     */
    private String sensorName;
    
    /**
     * 报警配置ID
     */
    private Long alarmConfigId;
    
    /**
     * 报警类型
     */
    private String alarmType;
    
    /**
     * 报警严重程度
     */
    private String severity;
    
    /**
     * 报警消息
     */
    private String message;
    
    /**
     * 触发数值
     */
    private Double triggerValue;
    
    /**
     * 阈值最小值
     */
    private Double thresholdMin;
    
    /**
     * 阈值最大值
     */
    private Double thresholdMax;
    
    /**
     * 触发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime triggerTime;
    
    /**
     * 确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acknowledgedTime;
    
    /**
     * 确认人ID
     */
    private Long acknowledgedBy;
    
    /**
     * 确认备注
     */
    private String acknowledgedComment;
    
    /**
     * 解决时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime resolveTime;
    
    /**
     * 解决人ID
     */
    private Long resolvedBy;
    
    /**
     * 解决方案
     */
    private String resolution;
    
    /**
     * 报警状态
     */
    private String status;
    
    /**
     * 持续时间（秒）
     */
    private Long durationSeconds;
    
    /**
     * 持续时间（人类可读格式）
     */
    private String durationHumanReadable;
    
    /**
     * 自定义字段（JSON格式）
     */
    private String customFields;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    /**
     * 源信息（网关-设备-传感器层级显示）
     */
    private String sourceInfo;
    
    /**
     * 报警详情
     */
    private String alarmDetails;
    
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
     * 判断是否为严重报警
     */
    public boolean isCritical() {
        return "CRITICAL".equals(severity) || "HIGH".equals(severity);
    }
    
    /**
     * 获取报警类型描述
     */
    public String getAlarmTypeDescription() {
        switch (alarmType) {
            case "TEMPERATURE_HIGH":
                return "温度过高";
            case "TEMPERATURE_LOW":
                return "温度过低";
            case "HUMIDITY_HIGH":
                return "湿度过高";
            case "HUMIDITY_LOW":
                return "湿度过低";
            case "BATTERY_LOW":
                return "电量过低";
            case "DEVICE_OFFLINE":
                return "设备离线";
            case "SENSOR_FAULT":
                return "传感器故障";
            case "COMMUNICATION_FAILURE":
                return "通信故障";
            default:
                return alarmType;
        }
    }
    
    /**
     * 获取严重程度描述
     */
    public String getSeverityDescription() {
        switch (severity) {
            case "CRITICAL":
                return "严重";
            case "HIGH":
                return "高";
            case "MEDIUM":
                return "中";
            case "LOW":
                return "低";
            case "INFO":
                return "信息";
            default:
                return severity;
        }
    }
    
    /**
     * 获取状态描述
     */
    public String getStatusDescription() {
        switch (status) {
            case "TRIGGERED":
                return "已触发";
            case "ACKNOWLEDGED":
                return "已确认";
            case "RESOLVED":
                return "已解决";
            default:
                return status;
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
            return "N/A";
        }
    }
    
    /**
     * 获取简要信息
     */
    public String getBriefInfo() {
        return String.format("[%s] %s %s", 
                getSeverityDescription(),
                getAlarmTypeDescription(),
                message != null ? message : "");
    }
    
    /**
     * 获取报警配置ID
     */
    public Long getAlarmConfigId() {
        return alarmConfigId;
    }
    
    /**
     * 设置报警配置ID
     */
    public void setAlarmConfigId(Long alarmConfigId) {
        this.alarmConfigId = alarmConfigId;
    }
}