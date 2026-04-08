package com.iot.temphumidity.enums;

/**
 * 目标类型枚举
 * 用于标识报警规则作用的目标类型
 */
public enum TargetType {
    /**
     * 设备目标
     */
    DEVICE("device", "设备"),
    
    /**
     * 传感器标签
     */
    SENSOR_TAG("sensor_tag", "传感器标签"),
    
    /**
     * 网关
     */
    GATEWAY("gateway", "网关"),
    
    /**
     * 区域/位置
     */
    AREA("area", "区域"),
    
    /**
     * 设备组
     */
    DEVICE_GROUP("device_group", "设备组"),
    
    /**
     * 用户组
     */
    USER_GROUP("user_group", "用户组"),
    
    /**
     * 系统级别
     */
    SYSTEM("system", "系统");
    
    private final String code;
    private final String description;
    
    TargetType(String code, String description) {
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
    public static TargetType fromCode(String code) {
        for (TargetType type : TargetType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的目标类型: " + code);
    }
    
    /**
     * 检查目标类型是否支持多个目标
     */
    public boolean supportsMultipleTargets() {
        return this == AREA || this == DEVICE_GROUP || this == USER_GROUP || this == SYSTEM;
    }
    
    /**
     * 检查目标类型是否支持单个目标
     */
    public boolean supportsSingleTarget() {
        return this == DEVICE || this == SENSOR_TAG || this == GATEWAY;
    }
    
    /**
     * 获取关联的实体类型
     */
    public String getRelatedEntityType() {
        switch (this) {
            case DEVICE:
                return "Device";
            case SENSOR_TAG:
                return "SensorTag";
            case GATEWAY:
                return "Gateway";
            case AREA:
                return "Area";
            case DEVICE_GROUP:
                return "DeviceGroup";
            case USER_GROUP:
                return "UserGroup";
            case SYSTEM:
                return "System";
            default:
                return "";
        }
    }
    
    /**
     * 获取默认的关联字段名
     */
    public String getDefaultReferenceField() {
        switch (this) {
            case DEVICE:
                return "device_id";
            case SENSOR_TAG:
                return "device_tag_id";
            case GATEWAY:
                return "gateway_id";
            case AREA:
                return "area_id";
            case DEVICE_GROUP:
                return "device_group_id";
            case USER_GROUP:
                return "user_group_id";
            case SYSTEM:
                return "system_id";
            default:
                return "";
        }
    }
    
    /**
     * 检查是否支持层级结构
     */
    public boolean supportsHierarchy() {
        return this == AREA || this == DEVICE_GROUP || this == USER_GROUP;
    }
    
    /**
     * 检查是否支持权限控制
     */
    public boolean supportsPermissionControl() {
        return this != SYSTEM;
    }
    
    /**
     * 获取可用的通知级别
     */
    public String[] getSupportedNotificationLevels() {
        switch (this) {
            case SYSTEM:
                return new String[]{"system_admin", "all_users"};
            case USER_GROUP:
                return new String[]{"group_admin", "group_members"};
            case DEVICE:
            case SENSOR_TAG:
            case GATEWAY:
                return new String[]{"device_owner", "device_users", "area_admin"};
            case AREA:
                return new String[]{"area_admin", "area_users"};
            case DEVICE_GROUP:
                return new String[]{"group_admin", "group_members"};
            default:
                return new String[]{};
        }
    }
}