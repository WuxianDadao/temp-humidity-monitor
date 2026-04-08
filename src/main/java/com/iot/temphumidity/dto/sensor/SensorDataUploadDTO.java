package com.iot.temphumidity.dto.sensor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 传感器数据上传DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataUploadDTO {
    
    @NotNull(message = "设备ID不能为空")
    @Size(min = 1, max = 50, message = "设备ID长度必须在1-50之间")
    private String deviceId;
    
    @NotNull(message = "网关ID不能为空")
    @Size(min = 1, max = 50, message = "网关ID长度必须在1-50之间")
    private String gatewayId;
    
    @NotNull(message = "数据列表不能为空")
    @Size(min = 1, message = "至少需要上传一条数据")
    private List<SensorDataItemDTO> dataList;
    
    private LocalDateTime uploadTime;
    private String protocolVersion;
    private String signature;
    private String encryptionKeyId;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorDataItemDTO {
        @NotNull(message = "传感器ID不能为空")
        @Size(min = 1, max = 50, message = "传感器ID长度必须在1-50之间")
        private String sensorId;
        
        private BigDecimal temperature;
        private BigDecimal humidity;
        private BigDecimal battery;
        private Integer rssi;
        
        private LocalDateTime timestamp;
        
        private String location;
        private String sensorType;
        
        private BigDecimal latitude;
        private BigDecimal longitude;
        private BigDecimal altitude;
        
        private Integer dataQuality;
        private String errorCode;
        private String additionalInfo;
    }
}