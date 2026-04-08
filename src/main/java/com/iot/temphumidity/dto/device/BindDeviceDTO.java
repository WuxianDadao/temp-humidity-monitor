package com.iot.temphumidity.dto.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "绑定设备数据传输对象")
public class BindDeviceDTO {
    
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    
    @NotNull(message = "设备ID不能为空")
    @Schema(description = "设备ID", example = "1")
    private Long deviceId;
    
    @Schema(description = "绑定备注", example = "用户绑定设备用于监控")
    private String remark;
}