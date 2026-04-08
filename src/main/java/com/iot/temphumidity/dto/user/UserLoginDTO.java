package com.iot.temphumidity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "用户登录数据传输对象")
public class UserLoginDTO {
    
    @NotBlank(message = "用户名或邮箱不能为空")
    @Schema(description = "用户名或邮箱", example = "john_doe")
    private String usernameOrEmail;
    
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "password123")
    private String password;
    
    @Schema(description = "是否记住登录状态", example = "true")
    private Boolean rememberMe;
}