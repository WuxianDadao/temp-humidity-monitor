package com.iot.temphumidity.mapper;

import org.mapstruct.Mapper;

/**
 * 通用映射器
 * 提供一些通用的映射方法
 */
@Mapper(componentModel = "spring")
public interface CommonMapper {
    
    /**
     * 字符串转长整型
     */
    default Long stringToLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 长整型转字符串
     */
    default String longToString(Long value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }
    
    /**
     * 字符串转整数
     */
    default Integer stringToInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 整数转字符串
     */
    default String integerToString(Integer value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }
    
    /**
     * 字符串转双精度浮点数
     */
    default Double stringToDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 双精度浮点数转字符串
     */
    default String doubleToString(Double value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }
    
    /**
     * 布尔值转字符串
     */
    default String booleanToString(Boolean value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
    
    /**
     * 字符串转布尔值
     */
    default Boolean stringToBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * 枚举转字符串
     */
    default <T extends Enum<T>> String enumToString(T enumValue) {
        if (enumValue == null) {
            return null;
        }
        return enumValue.name();
    }
    
    /**
     * 字符串转枚举
     */
    default <T extends Enum<T>> T stringToEnum(String value, Class<T> enumClass) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}