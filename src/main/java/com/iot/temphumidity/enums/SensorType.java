package com.iot.temphumidity.enums;

/**
 * 传感器类型枚举
 */
public enum SensorType {
    /**
     * 温湿度传感器
     */
    TEMPERATURE_HUMIDITY("温湿度传感器"),
    
    /**
     * 温度传感器
     */
    TEMPERATURE_ONLY("温度传感器"),
    
    /**
     * 湿度传感器
     */
    HUMIDITY_ONLY("湿度传感器"),
    
    /**
     * 压力传感器
     */
    PRESSURE("压力传感器"),
    
    /**
     * 光传感器
     */
    LIGHT("光传感器"),
    
    /**
     * 二氧化碳传感器
     */
    CO2("二氧化碳传感器"),
    
    /**
     * 空气质量传感器
     */
    AIR_QUALITY("空气质量传感器"),
    
    /**
     * 其他传感器
     */
    OTHER("其他传感器");
    
    private final String description;
    
    SensorType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 判断是否是温湿度相关的传感器
     */
    public boolean isTemperatureHumidityRelated() {
        return this == TEMPERATURE_HUMIDITY || this == TEMPERATURE_ONLY || this == HUMIDITY_ONLY;
    }
    
    /**
     * 根据描述获取传感器类型
     */
    public static SensorType fromDescription(String description) {
        for (SensorType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        return OTHER;
    }
}