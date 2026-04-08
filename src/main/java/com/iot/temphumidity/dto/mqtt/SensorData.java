package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * MQTT传感器数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    private String sensorId;
    private Double temperature;
    private Double humidity;
    private Double battery;
    private Integer rssi;
    private java.time.LocalDateTime timestamp;
}