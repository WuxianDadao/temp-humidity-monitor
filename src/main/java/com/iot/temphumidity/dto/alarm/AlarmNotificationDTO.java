package com.iot.temphumidity.dto.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "报警通知数据传输对象")
public class AlarmNotificationDTO {
    
    @Schema(description = "通知ID", example = "1")
    private Long id;
    
    @Schema(description = "关联的报警事件ID", example = "1")
    private Long alarmEventId;
    
    @Schema(description = "通知类型", example = "ALARM_TRIGGERED")
    private String notificationType;
    
    @Schema(description = "接收人", example = "user@example.com")
    private String recipient;
    
    @Schema(description = "通知渠道", example = "EMAIL")
    private String channel;
    
    @Schema(description = "通知标题", example = "温湿度报警通知")
    private String title;
    
    @Schema(description = "通知内容", example = "温度超过阈值，请及时处理")
    private String content;
    
    @Schema(description = "通知优先级", example = "HIGH")
    private String priority;
    
    @Schema(description = "发送状态", example = "SENT")
    private String status;
    
    @Schema(description = "发送结果", example = "SUCCESS")
    private String result;
    
    @Schema(description = "失败原因", example = "邮件服务器不可用")
    private String failureReason;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "发送时间", example = "2024-01-01 10:00:00")
    private LocalDateTime sentAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间", example = "2024-01-01 09:55:00")
    private LocalDateTime createdAt;
}