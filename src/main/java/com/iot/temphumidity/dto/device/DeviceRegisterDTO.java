package com.iot.temphumidity.dto.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@Schema(description = "设备注册数据传输对象")
public class DeviceRegisterDTO {
    
    @NotBlank(message = "设备唯一标识不能为空")
    @Pattern(regexp = "^[A-Za-z0-9_-]{6,50}$", message = "设备标识格式不正确，只能包含字母、数字、下划线和连字符，长度6-50")
    @Schema(description = "设备唯一标识(ICCID/IMEI/SERIAL)", example = "12345678901234567890")
    private String deviceIdentifier;
    
    @NotBlank(message = "设备类型不能为空")
    @Schema(description = "设备类型", example = "4G_GATEWAY")
    private String deviceType;
    
    @NotBlank(message = "设备名称不能为空")
    @Schema(description = "设备名称", example = "仓库网关设备")
    private String deviceName;
    
    @Schema(description = "设备型号", example = "GW-1000")
    private String deviceModel;
    
    @Schema(description = "设备厂商", example = "华为")
    private String manufacturer;
    
    @Schema(description = "固件版本", example = "v1.0.0")
    private String firmwareVersion;
    
    @Schema(description = "设备描述", example = "4G网关设备，用于仓库温湿度监控")
    private String description;
    
    @Schema(description = "安装位置", example = "仓库A区")
    private String location;
    
    @Schema(description = "纬度", example = "39.9042")
    private Double latitude;
    
    @Schema(description = "经度", example = "116.4074")
    private Double longitude;
}