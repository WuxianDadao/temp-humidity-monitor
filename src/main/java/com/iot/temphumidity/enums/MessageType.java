package com.iot.temphumidity.enums;

public enum MessageType {
    
    SYSTEM("system", "系统消息"),
    ALARM("alarm", "报警消息"),
    NOTIFICATION("notification", "通知消息"),
    DEVICE("device", "设备消息"),
    DATA("data", "数据消息"),
    CONFIG("config", "配置消息"),
    COMMAND("command", "命令消息");
    
    private final String code;
    private final String name;
    
    MessageType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public static MessageType fromCode(String code) {
        for (MessageType type : MessageType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的消息类型: " + code);
    }
    
    public static MessageType fromName(String name) {
        for (MessageType type : MessageType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的消息类型名称: " + name);
    }
}