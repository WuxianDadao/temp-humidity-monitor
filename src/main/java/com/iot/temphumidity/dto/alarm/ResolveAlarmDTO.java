package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "解决报警数据传输对象")
public class ResolveAlarmDTO {
    
    @NotBlank(message = "解决人不能为空")
    @Schema(description = "解决人", example = "admin")
    private String resolvedBy;
    
    @NotBlank(message = "解决备注不能为空")
    @Schema(description = "解决备注", example = "已调整空调温度，问题已解决")
    private String resolutionNotes;
}