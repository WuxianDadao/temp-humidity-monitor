package com.iot.temphumidity.enums;

/**
 * 设备类型枚举
 */
public enum DeviceType {
    GATEWAY("网关"),
    SENSOR_TAG("传感器标签"),
    TEMPERATURE_SENSOR("温度传感器"),
    HUMIDITY_SENSOR("湿度传感器"),
    TEMP_HUMIDITY_SENSOR("温湿度传感器"),
    PRESSURE_SENSOR("压力传感器"),
    LIGHT_SENSOR("光照传感器"),
    GAS_SENSOR("气体传感器"),
    MOTION_SENSOR("运动传感器"),
    DOOR_SENSOR("门磁传感器"),
    WATER_SENSOR("水浸传感器"),
    SMOKE_SENSOR("烟雾传感器"),
    CONTROLLER("控制器"),
    ACTUATOR("执行器");

    private final String description;

    DeviceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}