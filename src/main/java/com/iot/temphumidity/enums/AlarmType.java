package com.iot.temphumidity.enums;

public enum AlarmType {
    
    TEMPERATURE_HIGH("temperature_high", "温度过高", AlarmLevel.WARNING),
    TEMPERATURE_LOW("temperature_low", "温度过低", AlarmLevel.WARNING),
    HUMIDITY_HIGH("humidity_high", "湿度过高", AlarmLevel.WARNING),
    HUMIDITY_LOW("humidity_low", "湿度过低", AlarmLevel.WARNING),
    BATTERY_LOW("battery_low", "电量过低", AlarmLevel.WARNING),
    DEVICE_OFFLINE("device_offline", "设备离线", AlarmLevel.ERROR),
    DATA_ABNORMAL("data_abnormal", "数据异常", AlarmLevel.ERROR),
    SENSOR_FAILURE("sensor_failure", "传感器故障", AlarmLevel.CRITICAL),
    NETWORK_FAILURE("network_failure", "网络故障", AlarmLevel.CRITICAL),
    SYSTEM_ERROR("system_error", "系统错误", AlarmLevel.CRITICAL);
    
    private final String code;
    private final String name;
    private final AlarmLevel defaultLevel;
    
    AlarmType(String code, String name, AlarmLevel defaultLevel) {
        this.code = code;
        this.name = name;
        this.defaultLevel = defaultLevel;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public AlarmLevel getDefaultLevel() {
        return defaultLevel;
    }
    
    public static AlarmType fromCode(String code) {
        for (AlarmType type : AlarmType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的报警类型: " + code);
    }
    
    public static AlarmType fromName(String name) {
        for (AlarmType type : AlarmType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的报警类型名称: " + name);
    }
    
    public boolean isTemperatureRelated() {
        return this == TEMPERATURE_HIGH || this == TEMPERATURE_LOW;
    }
    
    public boolean isHumidityRelated() {
        return this == HUMIDITY_HIGH || this == HUMIDITY_LOW;
    }
    
    public boolean isDeviceRelated() {
        return this == DEVICE_OFFLINE || this == SENSOR_FAILURE || this == BATTERY_LOW;
    }
    
    public boolean isSystemRelated() {
        return this == NETWORK_FAILURE || this == SYSTEM_ERROR || this == DATA_ABNORMAL;
    }
}