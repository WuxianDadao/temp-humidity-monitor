package com.iot.temphumidity.dto.gateway;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewayConfigDTO {
    private String gatewayId;
    private String configVersion;
    private Map<String, Object> mqttConfig;
    private Map<String, Object> sensorConfig;
    private Map<String, Object> dataReportConfig;
    private Map<String, Object> networkConfig;
    private Map<String, Object> powerConfig;
    private Map<String, Object> alarmConfig;
}
