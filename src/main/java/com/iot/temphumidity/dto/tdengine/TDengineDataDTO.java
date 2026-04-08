package com.iot.temphumidity.dto.tdengine;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class TDengineDataDTO {
    private LocalDateTime ts;
    private Double temperature;
    private Double humidity;
    private String sensorId;
    private String deviceId;
    private String location;
    private String sensorType;
    private Integer battery;
    private Integer rssi;
    private Map<String, Object> tags;
    private Map<String, Object> fields;
}
