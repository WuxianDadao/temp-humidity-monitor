package com.iot.temphumidity.dto.sensor;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 传感器数据DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataDTO {
    
    @NotNull(message = "传感器ID不能为空")
    @Size(min = 1, max = 50, message = "传感器ID长度必须在1-50之间")
    private String sensorId;
    
    @NotNull(message = "设备ID不能为空")
    @Size(min = 1, max = 50, message = "设备ID长度必须在1-50之间")
    private String deviceId;
    
    @DecimalMin(value = "-50.0", message = "温度不能低于-50°C")
    @DecimalMax(value = "100.0", message = "温度不能高于100°C")
    private BigDecimal temperature;
    
    @DecimalMin(value = "0.0", message = "湿度不能低于0%")
    @DecimalMax(value = "100.0", message = "湿度不能高于100%")
    private BigDecimal humidity;
    
    @DecimalMin(value = "0.0", message = "电量不能低于0%")
    @DecimalMax(value = "100.0", message = "电量不能高于100%")
    private BigDecimal battery;
    
    private Integer rssi;
    
    private LocalDateTime timestamp;
    
    private String location;
    
    private String sensorType;
}