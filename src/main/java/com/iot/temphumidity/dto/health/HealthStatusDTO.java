package com.iot.temphumidity.dto.health;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 健康状态DTO
 * 用于传输系统健康状态信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusDTO {
    
    /**
     * 系统状态
     */
    private SystemStatus status;
    
    /**
     * 检查时间
     */
    private LocalDateTime timestamp;
    
    /**
     * PostgreSQL连接状态
     */
    private boolean postgresqlConnected;
    
    /**
     * TDengine连接状态
     */
    private boolean tdengineConnected;
    
    /**
     * Redis连接状态
     */
    private boolean redisConnected;
    
    /**
     * 服务运行时长（秒）
     */
    private long uptimeSeconds;
    
    /**
     * 内存使用率（百分比）
     */
    private double memoryUsage;
    
    /**
     * CPU使用率（百分比）
     */
    private double cpuUsage;
    
    /**
     * 数据库连接数
     */
    private int databaseConnections;
    
    /**
     * 活跃网关数量
     */
    private int activeGateways;
    
    /**
     * 消息队列状态
     */
    private MqttStatus mqttStatus;
    
    /**
     * 错误信息（如果有）
     */
    private String errorMessage;
    
    /**
     * 建议措施
     */
    private String recommendation;
    
    /**
     * 服务名称
     */
    private String service;
    
    /**
     * 服务版本
     */
    private String version;
    
    /**
     * 详细信息
     */
    private String details;
    
    /**
     * 系统状态枚举
     */
    public enum SystemStatus {
        UP,      // 正常运行
        DOWN,    // 完全宕机
        DEGRADED // 降级运行
    }
    
    /**
     * MQTT状态枚举
     */
    public enum MqttStatus {
        CONNECTED,    // 已连接
        DISCONNECTED, // 断开连接
        CONNECTING    // 连接中
    }
}