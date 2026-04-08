package com.iot.temphumidity.pojo;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class ReportResult {
    private Boolean success;
    private String resultCode;
    private String resultMessage;
    private LocalDateTime processTime;
    private Long processDuration;
    private Integer dataPointsProcessed;
    private Integer dataPointsAccepted;
    private Integer dataPointsRejected;
}
