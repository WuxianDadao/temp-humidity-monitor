package com.iot.temphumidity.enums;

/**
 * 报警严重程度枚举
 */
public enum AlarmSeverity {
    /**
     * 信息级别 - 仅记录，不需要处理
     */
    INFO,
    
    /**
     * 警告级别 - 需要关注但非紧急
     */
    WARNING,
    
    /**
     * 一般级别 - 需要处理的问题
     */
    MINOR,
    
    /**
     * 重要级别 - 较严重的问题
     */
    MAJOR,
    
    /**
     * 严重级别 - 紧急需要立即处理
     */
    CRITICAL;
    
    /**
     * 获取枚举的描述信息
     */
    public String getDescription() {
        switch (this) {
            case INFO:
                return "信息级别 - 仅记录";
            case WARNING:
                return "警告级别 - 需要关注";
            case MINOR:
                return "一般级别 - 需要处理";
            case MAJOR:
                return "重要级别 - 较严重";
            case CRITICAL:
                return "严重级别 - 紧急处理";
            default:
                return this.name();
        }
    }
    
    /**
     * 判断是否为严重报警（需要立即处理）
     */
    public boolean isSevere() {
        return this == MAJOR || this == CRITICAL;
    }
    
    /**
     * 判断是否为一般报警（可以稍后处理）
     */
    public boolean isNormal() {
        return this == INFO || this == WARNING || this == MINOR;
    }
    
    /**
     * 获取显示名称
     */
    public String getDisplayName() {
        switch (this) {
            case INFO:
                return "信息";
            case WARNING:
                return "警告";
            case MINOR:
                return "一般";
            case MAJOR:
                return "重要";
            case CRITICAL:
                return "严重";
            default:
                return this.name();
        }
    }
    
    /**
     * 获取枚举代码
     */
    public String getCode() {
        return name();
    }
}