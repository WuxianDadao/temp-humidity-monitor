package com.iot.temphumidity.enums;

/**
 * 规则类型枚举
 */
public enum RuleType {
    /**
     * 温度告警规则
     */
    TEMPERATURE("temperature", "温度告警"),
    
    /**
     * 湿度告警规则
     */
    HUMIDITY("humidity", "湿度告警"),
    
    /**
     * 温湿度复合告警规则
     */
    TEMPERATURE_HUMIDITY("temperature_humidity", "温湿度告警"),
    
    /**
     * 设备离线规则
     */
    DEVICE_OFFLINE("device_offline", "设备离线告警"),
    
    /**
     * 数据异常规则
     */
    DATA_ANOMALY("data_anomaly", "数据异常告警"),
    
    /**
     * 电池电量告警
     */
    BATTERY("battery", "电池电量告警"),
    
    /**
     * 信号强度告警
     */
    SIGNAL_STRENGTH("signal_strength", "信号强度告警"),
    
    /**
     * 自定义规则
     */
    CUSTOM("custom", "自定义规则");
    
    private final String code;
    private final String description;
    
    RuleType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据code获取枚举
     */
    public static RuleType fromCode(String code) {
        for (RuleType type : RuleType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的规则类型: " + code);
    }
    
    /**
     * 检查规则类型是否与环境数据相关
     */
    public boolean isEnvironmental() {
        return this == TEMPERATURE || this == HUMIDITY || this == TEMPERATURE_HUMIDITY;
    }
    
    /**
     * 检查规则类型是否与设备状态相关
     */
    public boolean isDeviceStatus() {
        return this == DEVICE_OFFLINE || this == BATTERY || this == SIGNAL_STRENGTH;
    }
    
    /**
     * 检查规则类型是否支持阈值设置
     */
    public boolean supportsThresholds() {
        return isEnvironmental() || this == BATTERY || this == SIGNAL_STRENGTH;
    }
    
    /**
     * 获取支持的数据字段
     */
    public String[] getSupportedDataFields() {
        switch (this) {
            case TEMPERATURE:
                return new String[]{"temperature"};
            case HUMIDITY:
                return new String[]{"humidity"};
            case TEMPERATURE_HUMIDITY:
                return new String[]{"temperature", "humidity"};
            case BATTERY:
                return new String[]{"battery"};
            case SIGNAL_STRENGTH:
                return new String[]{"rssi"};
            case DEVICE_OFFLINE:
                return new String[]{"status"};
            case DATA_ANOMALY:
                return new String[]{"temperature", "humidity", "battery", "rssi"};
            case CUSTOM:
                return new String[]{}; // 自定义字段
            default:
                return new String[]{};
        }
    }
    
    /**
     * 获取默认的条件表达式模板
     */
    public String getDefaultConditionTemplate() {
        switch (this) {
            case TEMPERATURE:
                return "temperature < {min} OR temperature > {max}";
            case HUMIDITY:
                return "humidity < {min} OR humidity > {max}";
            case TEMPERATURE_HUMIDITY:
                return "(temperature < {temp_min} OR temperature > {temp_max}) OR (humidity < {hum_min} OR humidity > {hum_max})";
            case BATTERY:
                return "battery < {threshold}";
            case SIGNAL_STRENGTH:
                return "rssi < {threshold}";
            case DEVICE_OFFLINE:
                return "last_seen > {offline_threshold_seconds}";
            case DATA_ANOMALY:
                return "is_anomaly(temperature, humidity) == true";
            case CUSTOM:
                return "";
            default:
                return "";
        }
    }
}