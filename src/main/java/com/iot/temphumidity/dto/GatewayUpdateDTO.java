package com.iot.temphumidity.dto;

import lombok.Data;

@Data
public class GatewayUpdateDTO {
    private String gatewayName;
    private String location;
    private Double longitude;
    private Double latitude;
    private Integer heartbeatInterval;
    private Integer dataReportInterval;
    private String networkType;
    private String carrier;
    private String remark;
    private Boolean enabled;
}