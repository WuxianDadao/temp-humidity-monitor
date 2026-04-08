package com.iot.temphumidity.dto.sensortag;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 传感器标签DTO
 */
@Data
public class SensorTagDTO {
    
    /** 标签ID */
    private Long tagId;
    
    /** 传感器ID */
    private String sensorId;
    
    /** 标签名称 */
    private String tagName;
    
    /** 标签值 */
    private String tagValue;
    
    /** 标签颜色 (用于UI显示) */
    private String tagColor;
    
    /** 创建时间 */
    private LocalDateTime createdAt;
    
    /** 更新时间 */
    private LocalDateTime updatedAt;
}