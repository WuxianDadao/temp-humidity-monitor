package com.iot.temphumidity.dto.gateway;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "网关数据传输对象")
public class GatewayDTO {
    
    @Schema(description = "网关ID", example = "1")
    private Long id;
    
    @Schema(description = "网关名称", example = "4G网关-001")
    private String gatewayName;
    
    @Schema(description = "网关设备编号", example = "GW001")
    private String gatewaySerialNumber;
    
    @Schema(description = "SIM卡ICCID", example = "89860000000000000000")
    private String simIccid;
    
    @Schema(description = "网关型号", example = "4G-IoT-Gateway-V2.0")
    private String gatewayModel;
    
    @Schema(description = "固件版本", example = "1.0.0")
    private String firmwareVersion;
    
    @Schema(description = "安装位置", example = "仓库A区")
    private String location;
    
    @Schema(description = "经度", example = "116.397128")
    private Double longitude;
    
    @Schema(description = "纬度", example = "39.916527")
    private Double latitude;
    
    @Schema(description = "网络类型", example = "4G")
    private String networkType;
    
    @Schema(description = "运营商", example = "中国移动")
    private String carrier;
    
    @Schema(description = "心跳间隔（秒）", example = "300")
    private Integer heartbeatInterval;
    
    @Schema(description = "数据上报间隔（秒）", example = "60")
    private Integer dataReportInterval;
    
    @Schema(description = "是否在线", example = "true")
    private Boolean isOnline;
    
    @Schema(description = "最后心跳时间", example = "2026-03-31T04:30:00")
    private LocalDateTime lastHeartbeatTime;
    
    @Schema(description = "最后上报时间", example = "2026-03-31T04:30:00")
    private LocalDateTime lastReportTime;
    
    @Schema(description = "在线时长（小时）", example = "120.5")
    private Double onlineHours;
    
    @Schema(description = "已连接传感器数量", example = "10")
    private Integer connectedSensorCount;
    
    @Schema(description = "数据上报总数", example = "10000")
    private Long totalDataReports;
    
    @Schema(description = "网络信号强度", example = "-75")
    private Integer networkSignal;
    
    @Schema(description = "网络质量", example = "良好")
    private String networkQuality;
    
    @Schema(description = "电池电量（%）", example = "95.0")
    private Double batteryLevel;
    
    @Schema(description = "固件更新状态", example = "up_to_date")
    private String firmwareUpdateStatus;
    
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
    
    @Schema(description = "备注信息")
    private String remark;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}