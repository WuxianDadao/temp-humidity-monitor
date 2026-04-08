package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MqttConnectionStatus {
    private String deviceId;
    private String status;
    private LocalDateTime lastConnectionTime;
    private LocalDateTime lastDisconnectionTime;
    private Integer connectionCount;
    private Long uptimeSeconds;
    private Integer signalStrength;
    private String networkType;
    private String ipAddress;
    private Integer mqttQos;
}
