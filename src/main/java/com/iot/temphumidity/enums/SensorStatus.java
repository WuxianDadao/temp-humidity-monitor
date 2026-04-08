package com.iot.temphumidity.enums;

import lombok.Getter;

/**
 * 传感器状态枚举
 */
@Getter
public enum SensorStatus {
    
    /**
     * 未激活 - 传感器已添加但未激活
     */
    INACTIVE("未激活", 1),
    
    /**
     * 活跃 - 传感器正常工作
     */
    ACTIVE("活跃", 2),
    
    /**
     * 离线 - 传感器暂时离线
     */
    OFFLINE("离线", 3),
    
    /**
     * 异常 - 传感器数据异常
     */
    ABNORMAL("异常", 4),
    
    /**
     * 维护中 - 传感器正在维护
     */
    MAINTENANCE("维护中", 5),
    
    /**
     * 已停用 - 传感器已停用
     */
    DISABLED("已停用", 6),
    
    /**
     * 电池电量低 - 传感器电池电量低
     */
    LOW_BATTERY("电池电量低", 7),
    
    /**
     * 信号弱 - 传感器信号强度弱
     */
    WEAK_SIGNAL("信号弱", 8),
    
    /**
     * 超时 - 传感器长时间未上报数据
     */
    TIMEOUT("超时", 9);
    
    private final String displayName;
    private final int code;
    
    SensorStatus(String displayName, int code) {
        this.displayName = displayName;
        this.code = code;
    }
    
    /**
     * 根据编码获取枚举
     */
    public static SensorStatus fromCode(int code) {
        for (SensorStatus status : SensorStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的传感器状态编码: " + code);
    }
    
    /**
     * 检查是否为活跃状态（可正常工作）
     */
    public boolean isActive() {
        return this == ACTIVE;
    }
    
    /**
     * 检查是否为故障状态
     */
    public boolean isFaulty() {
        return this == ABNORMAL || 
               this == LOW_BATTERY || 
               this == WEAK_SIGNAL || 
               this == TIMEOUT ||
               this == DISABLED;
    }
    
    /**
     * 检查是否在线（可接收数据）
     */
    public boolean isOnline() {
        return this == ACTIVE || 
               this == LOW_BATTERY || 
               this == WEAK_SIGNAL;
    }
    
    /**
     * 检查是否需要维护
     */
    public boolean needsMaintenance() {
        return this == LOW_BATTERY || 
               this == WEAK_SIGNAL || 
               this == TIMEOUT ||
               this == ABNORMAL;
    }
    
    /**
     * 获取状态描述
     */
    public String getDescription() {
        switch (this) {
            case INACTIVE:
                return "传感器已添加但未激活，等待首次数据上报";
            case ACTIVE:
                return "传感器正常工作，数据上报正常";
            case OFFLINE:
                return "传感器暂时离线，可能网络中断";
            case ABNORMAL:
                return "传感器数据异常，需要检查设备状态";
            case MAINTENANCE:
                return "传感器正在维护中";
            case DISABLED:
                return "传感器已被停用";
            case LOW_BATTERY:
                return "传感器电池电量低，建议更换电池";
            case WEAK_SIGNAL:
                return "传感器信号强度弱，可能影响数据传输";
            case TIMEOUT:
                return "传感器长时间未上报数据，可能设备故障";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 获取状态颜色（用于UI显示）
     */
    public String getColor() {
        switch (this) {
            case ACTIVE:
                return "#52c41a"; // 绿色
            case INACTIVE:
                return "#8c8c8c"; // 灰色
            case OFFLINE:
                return "#faad14"; // 黄色
            case ABNORMAL:
            case LOW_BATTERY:
            case WEAK_SIGNAL:
            case TIMEOUT:
                return "#ff4d4f"; // 红色
            case MAINTENANCE:
                return "#722ed1"; // 紫色
            case DISABLED:
                return "#bfbfbf"; // 浅灰色
            default:
                return "#8c8c8c";
        }
    }
    
    /**
     * 获取状态优先级（用于排序）
     */
    public int getPriority() {
        switch (this) {
            case ABNORMAL:
            case LOW_BATTERY:
            case WEAK_SIGNAL:
            case TIMEOUT:
                return 1; // 最高优先级
            case OFFLINE:
                return 2;
            case MAINTENANCE:
                return 3;
            case INACTIVE:
                return 4;
            case ACTIVE:
                return 5; // 最低优先级
            case DISABLED:
                return 6;
            default:
                return 7;
        }
    }
}