package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "创建报警事件数据传输对象")
public class AlarmEventCreateDTO {
    
    @NotNull(message = "报警规则ID不能为空")
    @Schema(description = "关联的报警规则ID", example = "1")
    private Long alarmRuleId;
    
    @NotNull(message = "传感器标签ID不能为空")
    @Schema(description = "关联的传感器标签ID", example = "1")
    private Long sensorTagId;
    
    @NotNull(message = "设备ID不能为空")
    @Schema(description = "关联的设备ID", example = "1")
    private Long deviceId;
    
    @NotBlank(message = "报警类型不能为空")
    @Schema(description = "报警事件类型", example = "TEMPERATURE_HIGH")
    private String alarmType;
    
    @NotBlank(message = "报警级别不能为空")
    @Schema(description = "报警级别", example = "HIGH")
    private String severity;
    
    @NotNull(message = "触发数值不能为空")
    @Schema(description = "触发报警的数值", example = "35.5")
    private Double triggerValue;
    
    @NotNull(message = "阈值不能为空")
    @Schema(description = "报警阈值", example = "30.0")
    private Double threshold;
    
    @Schema(description = "报警消息", example = "温度超过阈值")
    private String message;
    
    @NotNull(message = "数据记录ID不能为空")
    @Schema(description = "关联的数据记录ID", example = "12345")
    private Long dataRecordId;
    
    @Schema(description = "通知方式", example = "EMAIL,SMS")
    private String notificationMethods;
}