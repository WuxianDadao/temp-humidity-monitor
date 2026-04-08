package com.iot.temphumidity.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户登录响应数据传输对象")
public class UserLoginResponseDTO {
    
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    
    @Schema(description = "用户名", example = "john_doe")
    private String username;
    
    @Schema(description = "邮箱", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "真实姓名", example = "张三")
    private String realName;
    
    @Schema(description = "角色", example = "ADMIN")
    private String role;
    
    @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    
    @Schema(description = "刷新令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
    
    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType;
    
    @Schema(description = "访问令牌过期时间(秒)", example = "3600")
    private Long expiresIn;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "登录时间", example = "2024-01-01 10:00:00")
    private LocalDateTime loginTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "令牌过期时间", example = "2024-01-01 11:00:00")
    private LocalDateTime expiresAt;
    
    @Schema(description = "绑定的设备数量", example = "5")
    private Integer boundDeviceCount;
    
    @Schema(description = "权限列表", example = "[\"device:read\", \"device:write\", \"alarm:manage\"]")
    private String[] permissions;
}