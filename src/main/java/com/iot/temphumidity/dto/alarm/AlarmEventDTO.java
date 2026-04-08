package com.iot.temphumidity.dto.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "报警事件数据传输对象")
public class AlarmEventDTO {
    
    @Schema(description = "报警事件ID", example = "1")
    private Long id;
    
    @Schema(description = "关联的报警规则ID", example = "1")
    private Long alarmRuleId;
    
    @Schema(description = "关联的传感器标签ID", example = "1")
    private Long sensorTagId;
    
    @Schema(description = "关联的设备ID", example = "1")
    private Long deviceId;
    
    @Schema(description = "报警事件类型", example = "TEMPERATURE_HIGH")
    private String alarmType;
    
    @Schema(description = "报警级别", example = "HIGH")
    private String severity;
    
    @Schema(description = "触发报警的数值", example = "35.5")
    private Double triggerValue;
    
    @Schema(description = "报警阈值", example = "30.0")
    private Double threshold;
    
    @Schema(description = "报警消息", example = "温度超过阈值")
    private String message;
    
    @Schema(description = "报警状态", example = "ACTIVE")
    private String status;
    
    @Schema(description = "确认状态", example = "PENDING")
    private String acknowledgeStatus;
    
    @Schema(description = "确认人", example = "admin")
    private String acknowledgedBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "确认时间", example = "2024-01-01 10:00:00")
    private LocalDateTime acknowledgedAt;
    
    @Schema(description = "解决状态", example = "UNRESOLVED")
    private String resolutionStatus;
    
    @Schema(description = "解决人", example = "admin")
    private String resolvedBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "解决时间", example = "2024-01-01 10:30:00")
    private LocalDateTime resolvedAt;
    
    @Schema(description = "解决备注", example = "已调整空调温度")
    private String resolutionNotes;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "报警触发时间", example = "2024-01-01 09:55:00")
    private LocalDateTime triggeredAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "报警结束时间", example = "2024-01-01 10:30:00")
    private LocalDateTime endedAt;
    
    @Schema(description = "关联的数据记录ID", example = "12345")
    private Long dataRecordId;
    
    @Schema(description = "是否已通知", example = "true")
    private Boolean notified;
    
    @Schema(description = "通知方式", example = "EMAIL,SMS")
    private String notificationMethods;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间", example = "2024-01-01 09:55:00")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间", example = "2024-01-01 10:00:00")
    private LocalDateTime updatedAt;
}