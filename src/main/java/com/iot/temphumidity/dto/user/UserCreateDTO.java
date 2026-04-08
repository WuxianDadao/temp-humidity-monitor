package com.iot.temphumidity.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建用户DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度必须在6-64之间")
    private String password;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    private String email;
    
    @Size(max = 20, message = "手机号长度不能超过20")
    @Pattern(regexp = "^[0-9+\\-() ]*$", message = "手机号格式不正确")
    private String phone;
    
    @Size(max = 50, message = "真实姓名长度不能超过50")
    private String realName;
    
    @Size(max = 100, message = "公司名称长度不能超过100")
    private String company;
    
    @Size(max = 128, message = "部门名称长度不能超过128")
    private String department;
    
    @Size(max = 200, message = "地址长度不能超过200")
    private String address;
    
    @Size(max = 20, message = "角色长度不能超过20")
    private String role;
    
    private Boolean enabled = true;
    private Boolean emailVerified = false;
    private Boolean phoneVerified = false;
    
    @Size(max = 500, message = "备注长度不能超过500")
    private String notes;
}