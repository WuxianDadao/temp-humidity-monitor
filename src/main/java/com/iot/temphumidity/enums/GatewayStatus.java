package com.iot.temphumidity.enums;

import lombok.Getter;

/**
 * 网关状态枚举
 */
@Getter
public enum GatewayStatus {
    
    /**
     * 已注册 - 网关已注册但还未上线
     */
    REGISTERED("已注册", 1),
    
    /**
     * 在线 - 网关当前在线
     */
    ONLINE("在线", 2),
    
    /**
     * 离线 - 网关当前离线
     */
    OFFLINE("离线", 3),
    
    /**
     * 异常 - 网关状态异常
     */
    ABNORMAL("异常", 4),
    
    /**
     * 维护中 - 网关正在维护
     */
    MAINTENANCE("维护中", 5),
    
    /**
     * 已停用 - 网关已停用
     */
    DISABLED("已停用", 6);
    
    private final String displayName;
    private final int code;
    
    GatewayStatus(String displayName, int code) {
        this.displayName = displayName;
        this.code = code;
    }
    
    /**
     * 根据编码获取枚举
     */
    public static GatewayStatus fromCode(int code) {
        for (GatewayStatus status : GatewayStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的网关状态编码: " + code);
    }
    
    /**
     * 根据显示名称获取枚举
     */
    public static GatewayStatus fromDisplayName(String displayName) {
        for (GatewayStatus status : GatewayStatus.values()) {
            if (status.getDisplayName().equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的网关状态: " + displayName);
    }
    
    /**
     * 检查是否为活跃状态（可接收数据）
     */
    public boolean isActive() {
        return this == REGISTERED || this == ONLINE;
    }
    
    /**
     * 检查是否为在线状态
     */
    public boolean isOnline() {
        return this == ONLINE;
    }
    
    /**
     * 检查是否为故障状态
     */
    public boolean isFaulty() {
        return this == ABNORMAL || this == DISABLED;
    }
    
    /**
     * 获取状态描述
     */
    public String getDescription() {
        switch (this) {
            case REGISTERED:
                return "网关已注册，等待首次上线连接";
            case ONLINE:
                return "网关在线，正常运行中";
            case OFFLINE:
                return "网关离线，可能网络中断";
            case ABNORMAL:
                return "网关状态异常，需要检查";
            case MAINTENANCE:
                return "网关正在维护中";
            case DISABLED:
                return "网关已停用";
            default:
                return "未知状态";
        }
    }
}