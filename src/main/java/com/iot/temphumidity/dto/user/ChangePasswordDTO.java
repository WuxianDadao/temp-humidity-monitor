package com.iot.temphumidity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "修改密码数据传输对象")
public class ChangePasswordDTO {
    
    @NotBlank(message = "当前密码不能为空")
    @Schema(description = "当前密码", example = "old_password123")
    private String currentPassword;
    
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 100, message = "新密码长度必须在6-100个字符之间")
    @Schema(description = "新密码", example = "new_password456")
    private String newPassword;
    
    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认新密码", example = "new_password456")
    private String confirmPassword;
}