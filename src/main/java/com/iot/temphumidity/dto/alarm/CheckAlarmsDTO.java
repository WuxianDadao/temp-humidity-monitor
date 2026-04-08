package com.iot.temphumidity.dto.alarm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 检查报警DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAlarmsDTO {
    
    @Size(max = 100, message = "设备ID列表不能超过100个")
    private List<String> deviceIds;
    
    @Size(max = 100, message = "传感器ID列表不能超过100个")
    private List<String> sensorIds;
    
    @Size(max = 50, message = "报警类型列表不能超过50个")
    private List<String> alarmTypes;
    
    private List<String> severities;
    
    private Boolean includeResolved = false;
    private Boolean includeFalseAlarms = false;
    
    private Boolean realtimeCheck = true;
    
    private Integer timeoutSeconds = 30;
    
    private String checkType;
}