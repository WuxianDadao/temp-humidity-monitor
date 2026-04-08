package com.iot.temphumidity.dto.mqtt;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * MQTT报告结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResult {
    private Boolean success;
    private String message;
    private String errorCode;
    private java.time.LocalDateTime processedTime;
    private Integer recordsProcessed;
}