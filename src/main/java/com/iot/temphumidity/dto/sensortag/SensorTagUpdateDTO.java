package com.iot.temphumidity.dto.sensortag;

import lombok.Data;
import jakarta.validation.constraints.Size;

/**
 * 更新传感器标签DTO
 */
@Data
public class SensorTagUpdateDTO {
    
    /** 标签名称 */
    @Size(max = 50, message = "标签名称不能超过50个字符")
    private String tagName;
    
    /** 标签值 */
    @Size(max = 100, message = "标签值不能超过100个字符")
    private String tagValue;
    
    /** 标签颜色 */
    @Size(max = 20, message = "标签颜色不能超过20个字符")
    private String tagColor;
}