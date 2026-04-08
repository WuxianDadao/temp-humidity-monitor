package com.iot.temphumidity.enums;

/**
 * 报警状态枚举
 */
public enum AlarmStatus {
    NEW("新建"),
    PENDING("待处理"),
    ACKNOWLEDGED("已确认"),
    RESOLVED("已解决"),
    IGNORED("已忽略"),
    FALSE_ALARM("误报"),
    EXPIRED("已过期"),
    CLOSED("已关闭"),
    ESCALATED("已升级"),
    INVESTIGATING("调查中");

    private final String description;

    AlarmStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    /**
     * 检查是否为活动状态
     */
    public boolean isActive() {
        return this == NEW || this == PENDING || this == ACKNOWLEDGED || this == INVESTIGATING;
    }
    
    /**
     * 检查是否为已解决状态
     */
    public boolean isResolved() {
        return this == RESOLVED || this == CLOSED || this == FALSE_ALARM;
    }
    
    /**
     * 检查是否为需要处理的状态
     */
    public boolean requiresAction() {
        return this == NEW || this == PENDING || this == ESCALATED;
    }
    
    /**
     * 获取状态值（用于数据库存储）
     */
    public String getValue() {
        return name().toLowerCase();
    }
    
    /**
     * 从字符串创建枚举
     */
    public static AlarmStatus fromValue(String value) {
        if (value == null) return null;
        try {
            return AlarmStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * 获取所有活动状态的枚举
     */
    public static AlarmStatus[] getActiveStatuses() {
        return new AlarmStatus[] { NEW, PENDING, ACKNOWLEDGED, INVESTIGATING };
    }
    
    /**
     * 获取所有已解决状态的枚举
     */
    public static AlarmStatus[] getResolvedStatuses() {
        return new AlarmStatus[] { RESOLVED, CLOSED, FALSE_ALARM };
    }
    
    /**
     * 获取状态显示顺序（用于UI排序）
     */
    public int getDisplayOrder() {
        switch (this) {
            case NEW: return 1;
            case ESCALATED: return 2;
            case INVESTIGATING: return 3;
            case PENDING: return 4;
            case ACKNOWLEDGED: return 5;
            case RESOLVED: return 6;
            case CLOSED: return 7;
            case FALSE_ALARM: return 8;
            case IGNORED: return 9;
            case EXPIRED: return 10;
            default: return 99;
        }
    }
}