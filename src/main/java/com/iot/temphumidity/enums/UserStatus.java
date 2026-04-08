package com.iot.temphumidity.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 */
@Getter
public enum UserStatus {
    
    /**
     * 活跃 - 账户正常可用
     */
    ACTIVE("活跃", 1),
    
    /**
     * 待激活 - 注册后等待激活
     */
    PENDING_ACTIVATION("待激活", 2),
    
    /**
     * 已禁用 - 管理员手动禁用
     */
    DISABLED("已禁用", 3),
    
    /**
     * 已锁定 - 密码错误次数过多
     */
    LOCKED("已锁定", 4),
    
    /**
     * 已过期 - 账户过期
     */
    EXPIRED("已过期", 5),
    
    /**
     * 已删除 - 逻辑删除
     */
    DELETED("已删除", 6);
    
    private final String displayName;
    private final int code;
    
    UserStatus(String displayName, int code) {
        this.displayName = displayName;
        this.code = code;
    }
    
    /**
     * 根据编码获取枚举
     */
    public static UserStatus fromCode(int code) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的用户状态编码: " + code);
    }
    
    /**
     * 检查账户是否可用
     */
    public boolean isUsable() {
        return this == ACTIVE;
    }
    
    /**
     * 检查账户是否需要激活
     */
    public boolean needsActivation() {
        return this == PENDING_ACTIVATION;
    }
    
    /**
     * 检查账户是否被阻止登录
     */
    public boolean isBlocked() {
        return this == DISABLED || this == LOCKED || this == DELETED;
    }
    
    /**
     * 获取状态描述
     */
    public String getDescription() {
        switch (this) {
            case ACTIVE:
                return "账户活跃，可以正常使用";
            case PENDING_ACTIVATION:
                return "账户等待激活，需要验证邮箱或手机";
            case DISABLED:
                return "账户已被管理员禁用";
            case LOCKED:
                return "账户因多次密码错误被锁定";
            case EXPIRED:
                return "账户已过期";
            case DELETED:
                return "账户已被删除";
            default:
                return "未知状态";
        }
    }
}