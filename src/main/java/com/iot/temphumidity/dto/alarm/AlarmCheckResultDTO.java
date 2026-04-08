package com.iot.temphumidity.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 报警检查结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCheckResultDTO {
    
    private String checkId;
    private LocalDateTime checkTime;
    private String checkType;
    
    private Integer totalDevicesChecked;
    private Integer totalSensorsChecked;
    private Integer activeAlarmsFound;
    private Integer potentialIssuesFound;
    
    private List<AlarmEventDTO> activeAlarms;
    private List<PotentialIssueDTO> potentialIssues;
    
    private Boolean hasCriticalAlarms;
    private Boolean hasUnacknowledgedAlarms;
    
    private String summary;
    private String recommendations;
    
    private Double checkDurationSeconds;
    private String checkStatus;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PotentialIssueDTO {
        private String issueId;
        private String deviceId;
        private String sensorId;
        private String issueType;
        private String description;
        private String severity;
        private String recommendation;
        private LocalDateTime detectedAt;
        private Double confidence;
    }
}