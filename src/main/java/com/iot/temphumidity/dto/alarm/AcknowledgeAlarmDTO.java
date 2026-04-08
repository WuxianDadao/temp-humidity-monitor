package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "确认报警数据传输对象")
public class AcknowledgeAlarmDTO {
    
    @NotBlank(message = "确认人不能为空")
    @Schema(description = "确认人", example = "admin")
    private String acknowledgedBy;
    
    @Schema(description = "确认备注", example = "已收到报警，正在处理")
    private String notes;
}