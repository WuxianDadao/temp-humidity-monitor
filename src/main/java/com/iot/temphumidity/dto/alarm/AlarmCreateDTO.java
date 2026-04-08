package com.iot.temphumidity.dto.alarm;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 创建报警DTO
 */
@Data
public class AlarmCreateDTO {
    
    /** 报警规则ID */
    @NotNull(message = "报警规则ID不能为空")
    private Long ruleId;
    
    /** 传感器ID */
    @NotBlank(message = "传感器ID不能为空")
    private String sensorId;
    
    /** 设备ID */
    @NotBlank(message = "设备ID不能为空")
    private String deviceId;
    
    /** 报警类型: temperature/humidity/battery */
    @NotBlank(message = "报警类型不能为空")
    private String alarmType;
    
    /** 报警时间 */
    @NotNull(message = "报警时间不能为空")
    private LocalDateTime alarmTime;
    
    /** 当前值 */
    @NotNull(message = "当前值不能为空")
    private BigDecimal currentValue;
    
    /** 阈值 */
    @NotNull(message = "阈值不能为空")
    private BigDecimal threshold;
    
    /** 报警级别: info/warning/error/critical */
    @NotBlank(message = "报警级别不能为空")
    private String severity;
    
    /** 报警描述 */
    private String description;
    
    /** 位置信息 */
    private String location;
}