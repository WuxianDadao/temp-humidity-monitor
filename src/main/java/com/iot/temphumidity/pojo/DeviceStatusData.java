package com.iot.temphumidity.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceStatusData {
    private String deviceId;
    private String status;
    private LocalDateTime lastUpdate;
    private Integer batteryLevel;
    private Integer signalStrength;
    private String networkType;
    private String firmwareVersion;
    private Boolean isCharging;
}
