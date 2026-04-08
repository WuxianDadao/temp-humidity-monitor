package com.iot.temphumidity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "更新用户角色数据传输对象")
public class UpdateUserRoleDTO {
    
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    
    @NotNull(message = "新角色不能为空")
    @Schema(description = "新角色", example = "ADMIN", allowableValues = {"ADMIN", "USER"})
    private String newRole;
    
    @Schema(description = "操作用户ID", example = "2")
    private Long operatorId;
}