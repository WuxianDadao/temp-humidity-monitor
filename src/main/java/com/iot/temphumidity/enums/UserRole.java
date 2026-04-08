package com.iot.temphumidity.enums;

import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRole {
    
    /**
     * 超级管理员 - 拥有所有权限
     */
    SUPER_ADMIN("超级管理员", 1),
    
    /**
     * 管理员 - 拥有大部分管理权限
     */
    ADMIN("管理员", 2),
    
    /**
     * 普通用户 - 基本的操作权限
     */
    NORMAL_USER("普通用户", 3),
    
    /**
     * 只读用户 - 只能查看不能操作
     */
    READ_ONLY_USER("只读用户", 4),
    
    /**
     * 设备管理员 - 只管理设备
     */
    DEVICE_MANAGER("设备管理员", 5),
    
    /**
     * 查看者 - 只能查看数据
     */
    VIEWER("查看者", 6),
    
    /**
     * 操作员 - 可以操作设备
     */
    OPERATOR("操作员", 7);
    
    private final String displayName;
    private final int code;
    
    UserRole(String displayName, int code) {
        this.displayName = displayName;
        this.code = code;
    }
    
    /**
     * 根据编码获取枚举
     */
    public static UserRole fromCode(int code) {
        for (UserRole role : UserRole.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("未知的用户角色编码: " + code);
    }
    
    /**
     * 检查是否有管理权限
     */
    public boolean hasAdminPermission() {
        return this == SUPER_ADMIN || this == ADMIN;
    }
    
    /**
     * 检查是否有设备管理权限
     */
    public boolean hasDeviceManagementPermission() {
        return this == SUPER_ADMIN || this == ADMIN || this == DEVICE_MANAGER;
    }
    
    /**
     * 获取角色描述
     */
    public String getDescription() {
        switch (this) {
            case SUPER_ADMIN:
                return "拥有系统所有权限，包括用户管理、系统配置等";
            case ADMIN:
                return "拥有大部分管理权限，可以管理设备和用户";
            case NORMAL_USER:
                return "普通用户，可以查看和管理自己的设备";
            case READ_ONLY_USER:
                return "只读用户，只能查看系统数据";
            case DEVICE_MANAGER:
                return "设备管理员，专注于设备管理，无用户管理权限";
            case VIEWER:
                return "查看者，只能查看系统数据";
            case OPERATOR:
                return "操作员，可以操作设备但不能进行管理";
            default:
                return "未知角色";
        }
    }
}