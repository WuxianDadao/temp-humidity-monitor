package com.iot.temphumidity.enums;

public enum StatisticsType {
    
    HOURLY("hourly", "按小时统计"),
    DAILY("daily", "按天统计"),
    WEEKLY("weekly", "按周统计"),
    MONTHLY("monthly", "按月统计"),
    YEARLY("yearly", "按年统计"),
    CUSTOM("custom", "自定义时间范围");
    
    private final String code;
    private final String description;
    
    StatisticsType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static StatisticsType fromCode(String code) {
        for (StatisticsType type : StatisticsType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的统计类型: " + code);
    }
}