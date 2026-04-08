package com.iot.temphumidity.entity.tdengine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * TDengine网关状态实体
 * 对应 gateway_status 超表
 * 注意: 这个类只是数据模型，不用于ORM映射
 * TDengine使用JdbcTemplate进行数据访问
 */
@Data
public class GatewayStatusTD {
    
    /**
     * 时间戳
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timestamp;
    
    /**
     * 在线状态
     */

    private Boolean onlineStatus;
    
    /**
     * CPU使用率 (%)
     */

    private Double cpuUsage;
    
    /**
     * 内存使用率 (%)
     */

    private Double memoryUsage;
    
    /**
     * 磁盘使用率 (%)
     */

    private Double diskUsage;
    
    /**
     * 网络延迟 (ms)
     */

    private Double networkLatency;
    
    /**
     * MQTT连接状态
     */

    private Boolean mqttConnected;
    
    /**
     * 最后心跳时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastHeartbeat;
    
    /**
     * 错误码
     */

    private Integer errorCode;
    
    /**
     * 错误信息
     */

    private String errorMessage;
    
    // ========== 标签字段 (TAG) ==========
    
    /**
     * 网关ID
     */

    private Long gatewayId;
    
    /**
     * 网关序列号 (ICCID)
     */

    private String gatewaySn;
    
    /**
     * 网关名称
     */

    private String gatewayName;
    
    /**
     * 位置
     */

    private String location;
    
    /**
     * IP地址
     */

    private String ipAddress;
    
    /**
     * 固件版本
     */

    private String firmwareVersion;
    
    /**
     * 扩展标签 (JSON格式)
     */

    private String tags;
    
    // ========== 业务方法 ==========
    
    /**
     * 检查网关是否健康
     */
    public boolean isHealthy() {
        if (!Boolean.TRUE.equals(onlineStatus)) return false;
        if (!Boolean.TRUE.equals(mqttConnected)) return false;
        if (cpuUsage != null && cpuUsage > 90) return false;
        if (memoryUsage != null && memoryUsage > 90) return false;
        if (diskUsage != null && diskUsage > 90) return false;
        if (networkLatency != null && networkLatency > 1000) return false; // 1秒以上延迟
        return errorCode == null || errorCode == 0;
    }
    
    /**
     * 获取健康状态级别
     */
    public String getHealthLevel() {
        if (!isHealthy()) return "UNHEALTHY";
        
        int score = 100;
        
        if (cpuUsage != null) {
            if (cpuUsage > 80) score -= 20;
            else if (cpuUsage > 60) score -= 10;
        }
        
        if (memoryUsage != null) {
            if (memoryUsage > 80) score -= 20;
            else if (memoryUsage > 60) score -= 10;
        }
        
        if (networkLatency != null) {
            if (networkLatency > 500) score -= 20;
            else if (networkLatency > 200) score -= 10;
        }
        
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 50) return "FAIR";
        return "POOR";
    }
    
    /**
     * 检查是否需要维护
     */
    public boolean needsMaintenance() {
        if (diskUsage != null && diskUsage > 85) return true;
        if (lastHeartbeat != null) {
            LocalDateTime now = LocalDateTime.now();
            long hoursSinceHeartbeat = java.time.Duration.between(lastHeartbeat, now).toHours();
            return hoursSinceHeartbeat > 24; // 24小时无心跳
        }
        return false;
    }
    
    /**
     * 获取离线时长（分钟）
     */
    public Long getOfflineDurationMinutes() {
        if (Boolean.TRUE.equals(onlineStatus)) return 0L;
        
        if (lastHeartbeat != null) {
            LocalDateTime now = LocalDateTime.now();
            return java.time.Duration.between(lastHeartbeat, now).toMinutes();
        }
        return null;
    }
    
    /**
     * 转换为状态字符串
     */
    public String toStatusString() {
        return String.format(
            "Gateway[%s]: %s, CPU:%.1f%%, Mem:%.1f%%, Disk:%.1f%%, Latency:%.1fms, MQTT:%s",
            gatewayName,
            onlineStatus ? "ONLINE" : "OFFLINE",
            cpuUsage != null ? cpuUsage : 0.0,
            memoryUsage != null ? memoryUsage : 0.0,
            diskUsage != null ? diskUsage : 0.0,
            networkLatency != null ? networkLatency : 0.0,
            mqttConnected != null ? (mqttConnected ? "CONNECTED" : "DISCONNECTED") : "UNKNOWN"
        );
    }
    
    /**
     * 获取错误信息摘要
     */
    public String getErrorSummary() {
        if (errorCode == null || errorCode == 0) return "No errors";
        
        StringBuilder summary = new StringBuilder();
        summary.append("Error ").append(errorCode);
        
        if (errorMessage != null && !errorMessage.isEmpty()) {
            summary.append(": ").append(errorMessage.length() > 50 ? errorMessage.substring(0, 47) + "..." : errorMessage);
        }
        
        return summary.toString();
    }
}