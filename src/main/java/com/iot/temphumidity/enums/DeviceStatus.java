package com.iot.temphumidity.enums;

/**
 * 设备状态枚举
 */
public enum DeviceStatus {
    ONLINE("在线"),
    OFFLINE("离线"),
    FAULT("故障"),
    MAINTENANCE("维护"),
    SUSPENDED("暂停"),
    DEPLOYED("已部署"),
    UNUSED("未使用");

    private final String description;

    DeviceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}