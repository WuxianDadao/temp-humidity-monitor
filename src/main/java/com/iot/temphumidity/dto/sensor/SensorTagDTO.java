package com.iot.temphumidity.dto.sensor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 传感器标签DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorTagDTO {
    
    private Long id;
    
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
    
    @Size(max = 32, message = "固件版本长度不能超过32")
    private String firmwareVersion;
    
    @Size(max = 32, message = "硬件版本长度不能超过32")
    private String hardwareVersion;
    
    private BigDecimal measurementRangeTempMin;
    private BigDecimal measurementRangeTempMax;
    private BigDecimal measurementRangeHumidityMin;
    private BigDecimal measurementRangeHumidityMax;
    private BigDecimal accuracyTemp;
    private BigDecimal accuracyHumidity;
    private BigDecimal resolutionTemp;
    private BigDecimal resolutionHumidity;
    private BigDecimal powerConsumption;
    
    @Size(max = 32, message = "电池类型长度不能超过32")
    private String batteryType;
    
    private BigDecimal batteryCapacity;
    private Integer expectedLifetimeDays;
    
    @Size(max = 32, message = "通信协议长度不能超过32")
    private String communicationProtocol;
    
    private BigDecimal communicationRange;
    
    @Size(max = 16, message = "状态长度不能超过16")
    private String status;
    
    private LocalDate manufactureDate;
    private LocalDate firstActivationDate;
    private LocalDate lastMaintenanceDate;
    private LocalDate nextMaintenanceDate;
    
    private Long ownerId;
    private Long currentLocationId;
    private Long deviceId;
    private Long gatewayId;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private BigDecimal lastReportedTemp;
    private BigDecimal lastReportedHumidity;
    private BigDecimal batteryLevel;
    private Integer signalStrength;
    private LocalDateTime lastReportTime;
    
    private String notes;
    private Boolean deployed;
    private String tagColor;
    private String tagValue;
    private String tagIcon;
}