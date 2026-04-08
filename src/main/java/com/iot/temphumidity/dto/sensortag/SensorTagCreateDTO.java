package com.iot.temphumidity.dto.sensortag;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 创建传感器标签DTO
 */
@Data
public class SensorTagCreateDTO {
    
    /** 传感器ID */
    @NotBlank(message = "传感器ID不能为空")
    private String sensorId;
    
    /** 标签名称 */
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称不能超过50个字符")
    private String tagName;
    
    /** 标签值 */
    @Size(max = 100, message = "标签值不能超过100个字符")
    private String tagValue;
    
    /** 标签颜色 */
    @Size(max = 20, message = "标签颜色不能超过20个字符")
    private String tagColor;
}