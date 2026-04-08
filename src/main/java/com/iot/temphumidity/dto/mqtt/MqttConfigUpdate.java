package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import java.util.Map;

@Data
public class MqttConfigUpdate {
    private String deviceId;
    private String configType;
    private Map<String, Object> configData;
    private String configVersion;
    private Boolean requireRestart;
}
