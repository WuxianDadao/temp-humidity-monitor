package com.iot.temphumidity.dto.tdengine;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class TDengineQueryDTO {
    private String stableName;
    private List<String> fields;
    private Map<String, Object> conditions;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String timeInterval;  // 时间间隔，如 1s, 1m, 1h, 1d
    private String groupBy;
    private String orderBy;
    private Integer limit;
    private Integer offset;
}
