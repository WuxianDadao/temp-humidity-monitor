package com.iot.temphumidity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "重置密码数据传输对象")
public class ResetPasswordDTO {
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "john.doe@example.com")
    private String email;
    
    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", example = "123456")
    private String verificationCode;
    
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 100, message = "新密码长度必须在6-100个字符之间")
    @Schema(description = "新密码", example = "new_password456")
    private String newPassword;
}