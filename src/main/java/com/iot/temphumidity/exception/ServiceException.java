package com.iot.temphumidity.exception;

/**
 * 服务层异常类
 */
public class ServiceException extends RuntimeException {
    
    private String errorCode;
    private String errorMessage;
    
    public ServiceException() {
        super();
    }
    
    public ServiceException(String message) {
        super(message);
        this.errorMessage = message;
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }
    
    public ServiceException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public ServiceException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString() {
        if (errorCode != null) {
            return String.format("ServiceException[code=%s, message=%s]", errorCode, errorMessage);
        }
        return String.format("ServiceException[message=%s]", errorMessage);
    }
}