package com.iot.temphumidity.dto.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 报警配置数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlarmConfigDTO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 报警配置名称
     */
    private String name;
    
    /**
     * 报警配置描述
     */
    private String description;
    
    /**
     * 传感器ID（可以为空，表示对所有传感器生效）
     */
    private String sensorId;
    
    /**
     * 网关ID（可以为空，表示对所有网关生效）
     */
    private Long gatewayId;
    
    /**
     * 设备ID（可以为空，表示对所有设备生效）
     */
    private Long deviceId;
    
    /**
     * 报警类型
     */
    private String alarmType;
    
    /**
     * 报警严重程度
     */
    private String severity;
    
    /**
     * 阈值最小值
     */
    private Double thresholdMin;
    
    /**
     * 阈值最大值
     */
    private Double thresholdMax;
    
    /**
     * 温度阈值最小值
     */
    private Double temperatureThresholdMin;
    
    /**
     * 温度阈值最大值
     */
    private Double temperatureThresholdMax;
    
    /**
     * 湿度阈值最小值
     */
    private Double humidityThresholdMin;
    
    /**
     * 湿度阈值最大值
     */
    private Double humidityThresholdMax;
    
    /**
     * 触发条件（如持续时间、连续次数等）
     */
    private String triggerCondition;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 报警动作（如通知方式、执行脚本等）
     */
    private String actions;
    
    /**
     * 静默时间（秒），0表示不静默
     */
    private Integer silentPeriod;
    
    /**
     * 最后触发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTriggerTime;
    
    /**
     * 触发次数统计
     */
    private Integer triggerCount;
    
    /**
     * 最大触发次数（0表示无限制）
     */
    private Integer maxTriggerCount;
    
    /**
     * 是否已过期
 */
    private Boolean expired;
    
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;
    
    /**
     * 优先级（数值越小优先级越高）
     */
    private Integer priority;
    
    /**
     * 自定义字段（JSON格式）
     */
    private String customFields;
    
    /**
     * 创建者ID
     */
    private Long createdBy;
    
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
     * 报警范围描述
     */
    private String rangeDescription;
    
    /**
     * 阈值描述
     */
    private String thresholdDescription;
    
    /**
     * 报警类型描述
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
     * 严重程度描述
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
     * 状态描述
     */
    public String getStatusDescription() {
        if (Boolean.TRUE.equals(expired)) {
            return "已过期";
        }
        
        if (Boolean.TRUE.equals(enabled)) {
            return "启用";
        } else {
            return "禁用";
        }
    }
    
    /**
     * 检查是否可以触发
     */
    public boolean canTrigger(Double value) {
        if (value == null || !Boolean.TRUE.equals(enabled) || Boolean.TRUE.equals(expired)) {
            return false;
        }
        
        // 检查静默期
        if (isInSilentPeriod()) {
            return false;
        }
        
        // 检查最大触发次数
        if (hasExceededMaxTriggerCount()) {
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
     * 获取动作描述
     */
    public String getActionsDescription() {
        if (actions == null || actions.isEmpty()) {
            return "无动作";
        }
        
        // 简单的动作描述
        if (actions.contains("EMAIL")) {
            return "邮件通知";
        } else if (actions.contains("SMS")) {
            return "短信通知";
        } else if (actions.contains("WEBHOOK")) {
            return "WebHook通知";
        } else {
            return "自定义动作";
        }
    }
    
    /**
     * 获取配置摘要信息
     */
    public String getSummary() {
        return String.format("%s [%s] %s", 
                name,
                getAlarmTypeDescription(),
                getThresholdDescription());
    }
    
    /**
     * 验证阈值设置
     * @return 验证结果
     */
    public boolean validateThresholds() {
        if (temperatureThresholdMin != null && temperatureThresholdMax != null) {
            if (temperatureThresholdMin >= temperatureThresholdMax) {
                return false;
            }
        }
        
        if (humidityThresholdMin != null && humidityThresholdMax != null) {
            if (humidityThresholdMin >= humidityThresholdMax) {
                return false;
            }
        }
        
        if (temperatureThresholdMin != null && temperatureThresholdMax == null) {
            return false;
        }
        
        if (temperatureThresholdMax != null && temperatureThresholdMin == null) {
            return false;
        }
        
        if (humidityThresholdMin != null && humidityThresholdMax == null) {
            return false;
        }
        
        if (humidityThresholdMax != null && humidityThresholdMin == null) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 获取条件类型（兼容性方法）
     * @return 条件类型
     */
    public String getConditionType() {
        return triggerCondition;
    }
}