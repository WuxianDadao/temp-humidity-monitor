package com.iot.temphumidity.enums;

public enum AlarmLevel {
    
    INFO("info", "信息", "#1890ff", "普通信息通知"),
    WARNING("warning", "警告", "#faad14", "需要关注的问题"),
    ERROR("error", "错误", "#ff4d4f", "需要立即处理的错误"),
    CRITICAL("critical", "严重", "#cf1322", "系统级严重问题");
    
    private final String code;
    private final String name;
    private final String color;
    private final String description;
    
    AlarmLevel(String code, String name, String color, String description) {
        this.code = code;
        this.name = name;
        this.color = color;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public String getColor() {
        return color;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static AlarmLevel fromCode(String code) {
        for (AlarmLevel level : AlarmLevel.values()) {
            if (level.code.equals(code)) {
                return level;
            }
        }
        throw new IllegalArgumentException("未知的报警级别: " + code);
    }
    
    public static AlarmLevel fromName(String name) {
        for (AlarmLevel level : AlarmLevel.values()) {
            if (level.name.equals(name)) {
                return level;
            }
        }
        throw new IllegalArgumentException("未知的报警级别名称: " + name);
    }
    
    public boolean isHighPriority() {
        return this == ERROR || this == CRITICAL;
    }
    
    public boolean isLowPriority() {
        return this == INFO || this == WARNING;
    }
    
    /**
     * 获取显示名称（别名方法）
     */
    public String getDisplayName() {
        return name;
    }
}