package com.iot.temphumidity.dto.user;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String displayName;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private String status;
    private List<String> roles;
    private List<String> permissions;
}
