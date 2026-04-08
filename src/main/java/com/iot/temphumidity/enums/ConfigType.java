package com.iot.temphumidity.enums;

import lombok.Getter;
import org.w3c.dom.Document;

/**
 * 配置类型枚举
 */
@Getter
public enum ConfigType {
    
    /**
     * 字符串类型
     */
    STRING("字符串", "string"),
    
    /**
     * 整数类型
     */
    INTEGER("整数", "integer"),
    
    /**
     * 长整数类型
     */
    LONG("长整数", "long"),
    
    /**
     * 浮点数类型
     */
    FLOAT("浮点数", "float"),
    
    /**
     * 双精度浮点数类型
     */
    DOUBLE("双精度浮点数", "double"),
    
    /**
     * 布尔类型
     */
    BOOLEAN("布尔值", "boolean"),
    
    /**
     * JSON格式
     */
    JSON("JSON", "json"),
    
    /**
     * XML格式
     */
    XML("XML", "xml"),
    
    /**
     * YAML格式
     */
    YAML("YAML", "yaml"),
    
    /**
     * 日期时间类型
     */
    DATETIME("日期时间", "datetime"),
    
    /**
     * 日期类型
     */
    DATE("日期", "date"),
    
    /**
     * 时间类型
     */
    TIME("时间", "time"),
    
    /**
     * URL类型
     */
    URL("URL", "url"),
    
    /**
     * 邮箱类型
     */
    EMAIL("邮箱", "email"),
    
    /**
     * 电话号码类型
     */
    PHONE("电话", "phone"),
    
    /**
     * 文件路径类型
     */
    FILE_PATH("文件路径", "file_path"),
    
    /**
     * 密码/加密类型
     */
    PASSWORD("密码", "password"),
    
    /**
     * 颜色代码类型
     */
    COLOR("颜色", "color"),
    
    /**
     * 枚举值类型
     */
    ENUM("枚举", "enum"),
    
    /**
     * 数组类型
     */
    ARRAY("数组", "array"),
    
    /**
     * 对象类型
     */
    OBJECT("对象", "object");
    
    private final String displayName;
    private final String code;
    
    ConfigType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }
    
    /**
     * 根据代码获取枚举
     */
    public static ConfigType fromCode(String code) {
        for (ConfigType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return STRING;
    }
    
    /**
     * 检查是否为数值类型
     */
    public boolean isNumeric() {
        return this == INTEGER || this == LONG || this == FLOAT || this == DOUBLE;
    }
    
    /**
     * 检查是否为文本类型
     */
    public boolean isText() {
        return this == STRING || this == JSON || this == XML || this == YAML;
    }
    
    /**
     * 检查是否为日期时间类型
     */
    public boolean isDateTime() {
        return this == DATETIME || this == DATE || this == TIME;
    }
    
    /**
     * 检查是否为布尔类型
     */
    public boolean isBoolean() {
        return this == BOOLEAN;
    }
    
    /**
     * 检查是否为复杂类型
     */
    public boolean isComplex() {
        return this == JSON || this == XML || this == YAML || this == ARRAY || this == OBJECT;
    }
    
    /**
     * 检查是否需要加密存储
     */
    public boolean isSensitive() {
        return this == PASSWORD || this == EMAIL || this == PHONE;
    }
    
    /**
     * 获取对应的Java类型
     */
    public Class<?> getJavaType() {
        switch (this) {
            case INTEGER:
                return Integer.class;
            case LONG:
                return Long.class;
            case FLOAT:
                return Float.class;
            case DOUBLE:
                return Double.class;
            case BOOLEAN:
                return Boolean.class;
            case DATETIME:
            case DATE:
            case TIME:
                return java.time.LocalDateTime.class;
            case EMAIL:
            case PHONE:
            case FILE_PATH:
            case URL:
            case PASSWORD:
            case COLOR:
                return String.class;
            case JSON:
                return com.fasterxml.jackson.databind.JsonNode.class;
            case XML:
                return org.w3c.dom.Document.class;
            case YAML:
                return org.yaml.snakeyaml.Yaml.class;
            case ARRAY:
                return java.util.List.class;
            case OBJECT:
                return java.util.Map.class;
            case ENUM:
                return Enum.class;
            default:
                return String.class;
        }
    }
    
    /**
     * 获取默认值
     */
    public String getDefaultValue() {
        switch (this) {
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return "0";
            case BOOLEAN:
                return "false";
            case DATETIME:
            case DATE:
            case TIME:
                return java.time.LocalDateTime.now().toString();
            case ARRAY:
                return "[]";
            case OBJECT:
                return "{}";
            case JSON:
                return "{}";
            case XML:
                return "<root></root>";
            case YAML:
                return "---\n";
            default:
                return "";
        }
    }
    
    /**
     * 验证值是否符合类型
     */
    public boolean validateValue(String value) {
        if (value == null) {
            return false;
        }
        
        try {
            switch (this) {
                case INTEGER:
                    Integer.parseInt(value);
                    return true;
                case LONG:
                    Long.parseLong(value);
                    return true;
                case FLOAT:
                    Float.parseFloat(value);
                    return true;
                case DOUBLE:
                    Double.parseDouble(value);
                    return true;
                case BOOLEAN:
                    String lower = value.toLowerCase();
                    return "true".equals(lower) || "false".equals(lower) || 
                           "1".equals(lower) || "0".equals(lower) ||
                           "yes".equals(lower) || "no".equals(lower);
                case DATETIME:
                    java.time.LocalDateTime.parse(value);
                    return true;
                case DATE:
                    java.time.LocalDate.parse(value);
                    return true;
                case TIME:
                    java.time.LocalTime.parse(value);
                    return true;
                case EMAIL:
                    return value.matches("^[A-Za-z0-9+_.-]+@(.+)$");
                case PHONE:
                    return value.matches("^[0-9\\-+() ]+$");
                case URL:
                    return value.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
                case JSON:
                    try {
                        new com.fasterxml.jackson.databind.ObjectMapper().readTree(value);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                case XML:
                    try {
                        javax.xml.parsers.DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder()
                                .parse(new java.io.ByteArrayInputStream(value.getBytes()));
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                case COLOR:
                    return value.matches("^#[0-9A-Fa-f]{6}$|^#[0-9A-Fa-f]{3}$|^rgb\\(\\d{1,3},\\d{1,3},\\d{1,3}\\)$");
                case FILE_PATH:
                    return !value.contains("..") && !value.startsWith("/") && !value.contains("\\");
                default:
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}