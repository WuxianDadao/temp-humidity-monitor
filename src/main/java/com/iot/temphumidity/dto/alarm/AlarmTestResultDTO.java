package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "报警测试结果数据传输对象")
public class AlarmTestResultDTO {
    
    @Schema(description = "是否触发报警", example = "true")
    private Boolean triggered;
    
    @Schema(description = "报警消息", example = "温度超过阈值")
    private String message;
    
    @Schema(description = "测试数值", example = "35.5")
    private Double testValue;
    
    @Schema(description = "报警阈值", example = "30.0")
    private Double threshold;
    
    @Schema(description = "建议操作", example = "检查空调系统")
    private String recommendation;
}