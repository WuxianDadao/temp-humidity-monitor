package com.iot.temphumidity.dto.user;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Integer expiresIn;
    private UserDTO user;
}
