package com.iot.temphumidity.dto;

import lombok.Data;

@Data
public class GatewayCreateDTO {
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
    private String remark;
}