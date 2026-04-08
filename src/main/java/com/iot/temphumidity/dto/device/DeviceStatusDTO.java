package com.iot.temphumidity.dto.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "设备状态数据传输对象")
public class DeviceStatusDTO {
    
    @Schema(description = "设备ID(UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    private String deviceId;
    
    @Schema(description = "设备标识(ICCID/IMEI)", example = "12345678901234567890")
    private String deviceIdentifier;
    
    @Schema(description = "设备名称", example = "仓库网关设备")
    private String deviceName;
    
    @Schema(description = "设备状态", example = "ONLINE")
    private String status;
    
    @Schema(description = "网络状态", example = "4G_CONNECTED")
    private String networkStatus;
    
    @Schema(description = "信号强度", example = "-75")
    private Integer signalStrength;
    
    @Schema(description = "电池电量", example = "85")
    private Integer batteryLevel;
    
    @Schema(description = "CPU使用率", example = "45.5")
    private Double cpuUsage;
    
    @Schema(description = "内存使用率", example = "60.2")
    private Double memoryUsage;
    
    @Schema(description = "磁盘使用率", example = "30.5")
    private Double diskUsage;
    
    @Schema(description = "固件版本", example = "v1.0.0")
    private String firmwareVersion;
    
    @Schema(description = "最后心跳时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastHeartbeatTime;
    
    @Schema(description = "最后数据上报时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastDataReportTime;
    
    @Schema(description = "在线时长(分钟)", example = "1200")
    private Long onlineDurationMinutes;
    
    @Schema(description = "是否在线", example = "true")
    private Boolean isOnline;
    
    @Schema(description = "温度传感器数量", example = "5")
    private Integer temperatureSensorCount;
    
    @Schema(description = "湿度传感器数量", example = "5")
    private Integer humiditySensorCount;
    
    @Schema(description = "系统运行时长(小时)", example = "48")
    private Long systemUptimeHours;
    
    @Schema(description = "数据包丢失率(%)", example = "0.5")
    private Double packetLossRate;
}