package com.iot.temphumidity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GatewayDTO {
    private Long id;
    private String gatewayName;
    private String gatewaySerialNumber;
    private String simIccid;
    private String gatewayModel;
    private String firmwareVersion;
    private String location;
    private Double longitude;
    private Double latitude;
    private String networkType;
    private String carrier;
    private Integer heartbeatInterval;
    private Integer dataReportInterval;
    private Boolean isOnline;
    private LocalDateTime lastHeartbeatTime;
    private LocalDateTime lastReportTime;
    private Double onlineHours;
    private Integer connectedSensorCount;
    private Long totalDataReports;
    private Integer networkSignal;
    private String networkQuality;
    private Double batteryLevel;
    private String firmwareUpdateStatus;
    private Boolean enabled;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}