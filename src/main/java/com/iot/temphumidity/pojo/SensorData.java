package com.iot.temphumidity.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SensorData {
    private String sensorId;
    private String deviceId;
    private Double temperature;
    private Double humidity;
    private LocalDateTime timestamp;
    private Integer batteryLevel;
    private Integer signalStrength;
    private String location;
    private String sensorType;
}
