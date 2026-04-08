package com.iot.temphumidity.dto.alarm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;

/**
 * 测试报警规则的DTO
 * 用于模拟传感器数据来测试报警规则
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestAlarmRuleDTO {
    
    /**
     * 传感器ID
     */
    @NotNull(message = "传感器ID不能为空")
    private String sensorId;
    
    /**
     * 设备ID
     */
    @NotNull(message = "设备ID不能为空")
    private String deviceId;
    
    /**
     * 测试的温度值
     */
    @NotNull(message = "温度值不能为空")
    private Double temperature;
    
    /**
     * 测试的湿度值
     */
    @NotNull(message = "湿度值不能为空")
    private Double humidity;
    
    /**
     * 测试的电池电量
     */
    private Double battery;
    
    /**
     * 测试的信号强度
     */
    private Integer rssi;
    
    /**
     * 模拟的时间戳（毫秒）
     * 如果为空，则使用当前时间
     */
    private Long timestamp;
    
    /**
     * 测试场景描述
     */
    private String scenario;
    
    /**
     * 期望的报警类型
     * 可以是：TEMPERATURE_HIGH, TEMPERATURE_LOW, HUMIDITY_HIGH, HUMIDITY_LOW
     */
    private String expectedAlarmType;
    
    /**
     * 期望的报警严重程度
     * 可以是：CRITICAL, MAJOR, MINOR, INFO
     */
    private String expectedSeverity;
}