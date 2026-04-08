package com.iot.temphumidity.dto.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "更新报警规则状态数据传输对象")
public class UpdateAlarmRuleStatusDTO {
    
    @NotBlank(message = "规则状态不能为空")
    @Schema(description = "规则状态", example = "ACTIVE")
    private String status;
    
    @Schema(description = "状态变更原因", example = "设备维护完成，重新启用规则")
    private String reason;
    
    /**
     * 检查规则是否启用
     * @return 如果状态为ACTIVE或ENABLED，返回true
     */
    public boolean isEnabled() {
        return "ACTIVE".equalsIgnoreCase(status) || "ENABLED".equalsIgnoreCase(status);
    }
}