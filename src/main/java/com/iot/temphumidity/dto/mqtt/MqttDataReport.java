package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqttDataReport {
    private String deviceId;
    private String gatewayId;
    private LocalDateTime reportTime;
    private List<SensorData> sensorData;
    private DeviceStatusData deviceStatus;
    private ReportResult reportResult;
}
