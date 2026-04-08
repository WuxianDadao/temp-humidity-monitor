package com.iot.temphumidity.enums;

import lombok.Getter;

/**
 * 操作类型枚举
 */
@Getter
public enum OperationType {
    
    // ========== 通用操作 ==========
    LOGIN("登录", "user"),
    LOGOUT("登出", "user"),
    PASSWORD_CHANGE("修改密码", "user"),
    PROFILE_UPDATE("更新资料", "user"),
    
    // ========== 用户管理 ==========
    USER_CREATE("创建用户", "user_management"),
    USER_UPDATE("更新用户", "user_management"),
    USER_DELETE("删除用户", "user_management"),
    USER_ENABLE("启用用户", "user_management"),
    USER_DISABLE("禁用用户", "user_management"),
    USER_ROLE_UPDATE("更新用户角色", "user_management"),
    USER_PERMISSION_UPDATE("更新用户权限", "user_management"),
    
    // ========== 网关管理 ==========
    GATEWAY_REGISTER("网关注册", "gateway_management"),
    GATEWAY_UPDATE("更新网关", "gateway_management"),
    GATEWAY_DELETE("删除网关", "gateway_management"),
    GATEWAY_ENABLE("启用网关", "gateway_management"),
    GATEWAY_DISABLE("禁用网关", "gateway_management"),
    GATEWAY_CONFIG_UPDATE("更新网关配置", "gateway_management"),
    GATEWAY_BIND_USER("绑定网关用户", "gateway_management"),
    GATEWAY_UNBIND_USER("解绑网关用户", "gateway_management"),
    GATEWAY_REBOOT("重启网关", "gateway_management"),
    
    // ========== 设备管理 ==========
    DEVICE_REGISTER("设备注册", "device_management"),
    DEVICE_UPDATE("更新设备", "device_management"),
    DEVICE_DELETE("删除设备", "device_management"),
    DEVICE_ENABLE("启用设备", "device_management"),
    DEVICE_DISABLE("禁用设备", "device_management"),
    DEVICE_BIND_GATEWAY("绑定设备网关", "device_management"),
    DEVICE_UNBIND_GATEWAY("解绑设备网关", "device_management"),
    DEVICE_RELOCATE("设备搬迁", "device_management"),
    
    // ========== 传感器管理 ==========
    SENSOR_REGISTER("传感器注册", "sensor_management"),
    SENSOR_UPDATE("更新传感器", "sensor_management"),
    SENSOR_DELETE("删除传感器", "sensor_management"),
    SENSOR_ENABLE("启用传感器", "sensor_management"),
    SENSOR_DISABLE("禁用传感器", "sensor_management"),
    SENSOR_BIND_DEVICE("绑定传感器设备", "sensor_management"),
    SENSOR_UNBIND_DEVICE("解绑传感器设备", "sensor_management"),
    SENSOR_CONFIG_UPDATE("更新传感器配置", "sensor_management"),
    SENSOR_THRESHOLD_UPDATE("更新传感器阈值", "sensor_management"),
    
    // ========== 数据管理 ==========
    DATA_QUERY("数据查询", "data_management"),
    DATA_EXPORT("数据导出", "data_management"),
    DATA_DELETE("数据删除", "data_management"),
    DATA_CLEAN("数据清理", "data_management"),
    DATA_BACKUP("数据备份", "data_management"),
    DATA_RESTORE("数据恢复", "data_management"),
    
    // ========== 报警管理 ==========
    ALARM_ACKNOWLEDGE("确认报警", "alarm_management"),
    ALARM_RESOLVE("处理报警", "alarm_management"),
    ALARM_IGNORE("忽略报警", "alarm_management"),
    ALARM_DELETE("删除报警", "alarm_management"),
    ALARM_THRESHOLD_UPDATE("更新报警阈值", "alarm_management"),
    ALARM_RULE_UPDATE("更新报警规则", "alarm_management"),
    
    // ========== 系统配置 ==========
    CONFIG_CREATE("创建配置", "system_config"),
    CONFIG_UPDATE("更新配置", "system_config"),
    CONFIG_DELETE("删除配置", "system_config"),
    CONFIG_IMPORT("导入配置", "system_config"),
    CONFIG_EXPORT("导出配置", "system_config"),
    
    // ========== 权限管理 ==========
    ROLE_CREATE("创建角色", "permission_management"),
    ROLE_UPDATE("更新角色", "permission_management"),
    ROLE_DELETE("删除角色", "permission_management"),
    PERMISSION_CREATE("创建权限", "permission_management"),
    PERMISSION_UPDATE("更新权限", "permission_management"),
    PERMISSION_DELETE("删除权限", "permission_management"),
    
    // ========== 系统维护 ==========
    SYSTEM_REBOOT("系统重启", "system_maintenance"),
    SYSTEM_BACKUP("系统备份", "system_maintenance"),
    SYSTEM_RESTORE("系统恢复", "system_maintenance"),
    SYSTEM_UPDATE("系统更新", "system_maintenance"),
    SYSTEM_CLEANUP("系统清理", "system_maintenance"),
    
    // ========== API访问 ==========
    API_CALL("API调用", "api_access"),
    API_AUTH("API认证", "api_access"),
    API_RATE_LIMIT("API限流", "api_access"),
    
    // ========== MQTT操作 ==========
    MQTT_PUBLISH("MQTT发布", "mqtt_operation"),
    MQTT_SUBSCRIBE("MQTT订阅", "mqtt_operation"),
    MQTT_UNSUBSCRIBE("MQTT取消订阅", "mqtt_operation"),
    MQTT_CONNECT("MQTT连接", "mqtt_operation"),
    MQTT_DISCONNECT("MQTT断开连接", "mqtt_operation"),
    
    // ========== 其他操作 ==========
    FILE_UPLOAD("文件上传", "file_operation"),
    FILE_DOWNLOAD("文件下载", "file_operation"),
    FILE_DELETE("文件删除", "file_operation"),
    NOTIFICATION_SEND("发送通知", "notification"),
    REPORT_GENERATE("生成报告", "report"),
    AUDIT_LOG("审计日志", "audit"),
    
    // ========== 数据同步 ==========
    SYNC_START("开始同步", "data_sync"),
    SYNC_STOP("停止同步", "data_sync"),
    SYNC_PAUSE("暂停同步", "data_sync"),
    SYNC_RESUME("恢复同步", "data_sync"),
    SYNC_STATUS("同步状态", "data_sync");
    
    private final String displayName;
    private final String category;
    
    OperationType(String displayName, String category) {
        this.displayName = displayName;
        this.category = category;
    }
    
    /**
     * 根据显示名称获取枚举
     */
    public static OperationType fromDisplayName(String displayName) {
        for (OperationType type : values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 获取分类对应的操作类型
     */
    public static java.util.List<OperationType> getByCategory(String category) {
        java.util.List<OperationType> result = new java.util.ArrayList<>();
        for (OperationType type : values()) {
            if (type.getCategory().equals(category)) {
                result.add(type);
            }
        }
        return result;
    }
    
    /**
     * 检查是否为用户管理操作
     */
    public boolean isUserManagement() {
        return category.equals("user_management");
    }
    
    /**
     * 检查是否为设备管理操作
     */
    public boolean isDeviceManagement() {
        return category.equals("device_management");
    }
    
    /**
     * 检查是否为网关管理操作
     */
    public boolean isGatewayManagement() {
        return category.equals("gateway_management");
    }
    
    /**
     * 检查是否为传感器管理操作
     */
    public boolean isSensorManagement() {
        return category.equals("sensor_management");
    }
    
    /**
     * 检查是否为数据管理操作
     */
    public boolean isDataManagement() {
        return category.equals("data_management");
    }
    
    /**
     * 检查是否为报警管理操作
     */
    public boolean isAlarmManagement() {
        return category.equals("alarm_management");
    }
    
    /**
     * 检查是否为系统操作
     */
    public boolean isSystemOperation() {
        return category.equals("system_config") || 
               category.equals("system_maintenance") ||
               category.equals("data_sync");
    }
    
    /**
     * 检查是否为安全相关操作
     */
    public boolean isSecurityOperation() {
        return category.equals("permission_management") ||
               category.equals("api_access") ||
               category.equals("audit");
    }
    
    /**
     * 检查是否为高风险操作
     */
    public boolean isHighRiskOperation() {
        switch (this) {
            case USER_DELETE:
            case GATEWAY_DELETE:
            case DEVICE_DELETE:
            case SENSOR_DELETE:
            case DATA_DELETE:
            case SYSTEM_REBOOT:
            case SYSTEM_UPDATE:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * 检查是否为数据修改操作
     */
    public boolean isDataModification() {
        return name().endsWith("_CREATE") ||
               name().endsWith("_UPDATE") ||
               name().endsWith("_DELETE") ||
               name().endsWith("_CHANGE") ||
               name().endsWith("_ENABLE") ||
               name().endsWith("_DISABLE") ||
               name().endsWith("_BIND") ||
               name().endsWith("_UNBIND");
    }
    
    /**
     * 获取操作分类映射
     */
    public static java.util.Map<String, java.util.List<OperationType>> getCategoryMap() {
        java.util.Map<String, java.util.List<OperationType>> map = new java.util.HashMap<>();
        for (OperationType type : values()) {
            map.computeIfAbsent(type.getCategory(), k -> new java.util.ArrayList<>()).add(type);
        }
        return map;
    }
    
    /**
     * 获取操作类型的权限标识
     */
    public String getPermissionCode() {
        return "operation:" + name().toLowerCase().replace("_", ":");
    }
    
    /**
     * 获取操作说明
     */
    public String getDescription() {
        String baseDesc = displayName;
        if (isHighRiskOperation()) {
            baseDesc += "（高风险操作）";
        } else if (isSecurityOperation()) {
            baseDesc += "（安全操作）";
        }
        return baseDesc;
    }
    
    /**
     * 获取枚举代码（用于序列化）
     */
    public String getCode() {
        return name();
    }
    
    /**
     * 获取显示名称（别名方法）
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * 获取分类（别名方法）
     */
    public String getCategory() {
        return category;
    }
}