package com.iot.temphumidity.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "设备配置数据传输对象")
public class DeviceConfigDTO {
    
    @Schema(description = "设备配置ID", example = "1")
    private Long id;
    
    @Schema(description = "设备ID", example = "1")
    private Long deviceId;
    
    @Schema(description = "配置类型", example = "MQTT_CONFIG")
    private String configType;
    
    @Schema(description = "配置键", example = "mqtt.broker.url")
    private String configKey;
    
    @Schema(description = "配置值", example = "tcp://mqtt.example.com:1883")
    private String configValue;
    
    @Schema(description = "配置描述", example = "MQTT代理服务器地址")
    private String description;
    
    @Schema(description = "配置版本", example = "v1.0.0")
    private String version;
    
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
    
    @Schema(description = "扩展配置数据")
    private Map<String, Object> extraData;
}