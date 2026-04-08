package com.iot.temphumidity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "用户更新数据传输对象")
public class UserUpdateDTO {
    
    @Size(min = 2, max = 50, message = "真实姓名长度必须在2-50个字符之间")
    @Schema(description = "真实姓名", example = "张三")
    private String realName;
    
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "john.doe@example.com")
    private String email;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    @Schema(description = "公司名称", example = "物联网科技有限公司")
    private String company;
    
    @Schema(description = "职位", example = "系统管理员")
    private String position;
    
    @Schema(description = "备注", example = "系统管理员用户")
    private String remark;
    
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}