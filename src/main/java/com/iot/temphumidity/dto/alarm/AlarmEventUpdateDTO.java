package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "更新报警事件数据传输对象")
public class AlarmEventUpdateDTO {
    
    @NotBlank(message = "报警状态不能为空")
    @Schema(description = "报警状态", example = "ACKNOWLEDGED")
    private String status;
    
    @Schema(description = "报警消息", example = "已收到报警，正在处理")
    private String message;
    
    @Schema(description = "通知方式", example = "EMAIL,SMS")
    private String notificationMethods;
    
    @Schema(description = "解决人", example = "admin")
    private String resolvedBy;
    
    @Schema(description = "解决说明", example = "温度传感器故障，已更换")
    private String resolutionNotes;
    
    // 这些getter方法由Lombok自动生成
    // 这里显式声明以确保代码编译通过
    public String getResolvedBy() {
        return resolvedBy;
    }
    
    public String getResolutionNotes() {
        return resolutionNotes;
    }
}