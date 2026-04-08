package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Schema(description = "发送通知数据传输对象")
public class SendNotificationDTO {
    
    @NotBlank(message = "通知类型不能为空")
    @Schema(description = "通知类型", example = "ALARM_TRIGGERED")
    private String notificationType;
    
    @NotEmpty(message = "接收人列表不能为空")
    @Schema(description = "接收人列表（用户ID或邮箱）", example = "[\"user1@example.com\", \"user2@example.com\"]")
    private List<String> recipients;
    
    @Schema(description = "通知标题", example = "温湿度报警通知")
    private String title;
    
    @Schema(description = "通知内容", example = "温度超过阈值，请及时处理")
    private String content;
    
    @Schema(description = "通知优先级", example = "HIGH")
    private String priority;
    
    @Schema(description = "通知渠道", example = "EMAIL,SMS")
    private String channels;
}