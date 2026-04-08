package com.iot.temphumidity.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 警报检查结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertCheckResultDTO {
    
    private String checkId;
    private LocalDateTime checkTime;
    
    private Integer totalAlerts;
    private Integer activeAlerts;
    private Integer resolvedAlerts;
    
    private List<AlertSummaryDTO> alertsBySeverity;
    private List<AlertSummaryDTO> alertsByType;
    private List<AlertSummaryDTO> alertsByStatus;
    
    private List<AlertItemDTO> criticalAlerts;
    private List<AlertItemDTO> unacknowledgedAlerts;
    private List<AlertItemDTO> recentAlerts;
    
    private String overallStatus;
    private Boolean requiresAttention;
    private Boolean hasCriticalAlert;
    
    private String summary;
    private List<String> actionsRequired;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlertSummaryDTO {
        private String category;
        private String value;
        private Integer count;
        private Integer activeCount;
        private Integer resolvedCount;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlertItemDTO {
        private String alertId;
        private String deviceId;
        private String sensorId;
        private String alertType;
        private String severity;
        private String status;
        private String description;
        private LocalDateTime triggeredAt;
        private LocalDateTime acknowledgedAt;
        private LocalDateTime resolvedAt;
        private String assignedTo;
        private Boolean requiresAction;
        private String actionRequired;
    }
}