package com.iot.temphumidity.dto.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "添加传感器标签数据传输对象")
public class AddSensorTagDTO {
    
    @NotBlank(message = "传感器标签标识不能为空")
    @Schema(description = "传感器标签唯一标识", example = "TEMP001")
    private String tagIdentifier;
    
    @NotBlank(message = "传感器类型不能为空")
    @Schema(description = "传感器类型", example = "TEMPERATURE")
    private String sensorType;
    
    @NotBlank(message = "传感器名称不能为空")
    @Schema(description = "传感器名称", example = "仓库A区温度传感器")
    private String sensorName;
    
    @Schema(description = "传感器型号", example = "DS18B20")
    private String sensorModel;
    
    @Schema(description = "传感器厂商", example = "Maxim Integrated")
    private String manufacturer;
    
    @Schema(description = "测量范围最小值", example = "-40.0")
    private Double measurementMin;
    
    @Schema(description = "测量范围最大值", example = "85.0")
    private Double measurementMax;
    
    @Schema(description = "测量精度", example = "0.5")
    private Double accuracy;
    
    @Schema(description = "采样间隔(秒)", example = "60")
    private Integer samplingInterval;
    
    @Schema(description = "安装位置", example = "仓库A区货架1层")
    private String location;
    
    @Schema(description = "纬度", example = "39.9042")
    private Double latitude;
    
    @Schema(description = "经度", example = "116.4074")
    private Double longitude;
    
    @Schema(description = "传感器描述", example = "用于监测仓库A区温度")
    private String description;
}