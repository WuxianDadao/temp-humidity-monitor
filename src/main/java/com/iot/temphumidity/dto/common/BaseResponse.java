package com.iot.temphumidity.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BaseResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setCode("SUCCESS");
        response.setMessage("Operation successful");
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
    
    public static <T> BaseResponse<T> error(String code, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
}
