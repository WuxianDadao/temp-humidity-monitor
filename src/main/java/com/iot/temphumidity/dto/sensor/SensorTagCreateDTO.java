package com.iot.temphumidity.dto.sensor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 创建传感器标签DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorTagCreateDTO {
    
    @NotBlank(message = "设备序列号不能为空")
    @Size(min = 1, max = 32, message = "设备序列号长度必须在1-32之间")
    private String serialNumber;
    
    @Size(max = 128, message = "标签名称长度不能超过128")
    private String tagName;
    
    @Size(max = 32, message = "传感器类型长度不能超过32")
    private String sensorType;
    
    @Size(max = 64, message = "型号长度不能超过64")
    private String model;
    
    @Size(max = 64, message = "制造商长度不能超过64")
    private String manufacturer;
    
    private BigDecimal measurementRangeTempMin;
    private BigDecimal measurementRangeTempMax;
    private BigDecimal measurementRangeHumidityMin;
    private BigDecimal measurementRangeHumidityMax;
    private BigDecimal accuracyTemp;
    private BigDecimal accuracyHumidity;
    
    @Size(max = 32, message = "通信协议长度不能超过32")
    private String communicationProtocol;
    
    @Size(max = 32, message = "电池类型长度不能超过32")
    private String batteryType;
    
    private BigDecimal batteryCapacity;
    private Integer expectedLifetimeDays;
    
    private Long deviceId;
    private Long gatewayId;
    
    private LocalDate manufactureDate;
}