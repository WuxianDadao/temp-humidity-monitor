package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * MQTT设备状态数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStatusData {
    private String deviceId;
    private String status;
    private Double battery;
    private Integer rssi;
    private java.time.LocalDateTime lastSeen;
    private String firmwareVersion;
    private String simCardId;
}