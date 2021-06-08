package com.kakao.bizplatform.common.exception;

public class ApiCommonException extends RuntimeException{
    private String code;
    private String message;

    public ApiCommonException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiCommonException(String message) {
        super(message);
    }
}