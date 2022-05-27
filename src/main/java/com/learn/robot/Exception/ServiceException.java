package com.learn.robot.Exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    private String code;

    private String message;

    private String message_en;


    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message,String message_en) {
        super(message);
        this.code = code;
        this.message_en = message_en;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return this.code + ":" + this.getMessage();
    }
}
