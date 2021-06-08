package com.kakao.bizplatform.navi.exception;

import com.kakao.bizplatform.common.enumeration.ErrorCode;
import com.kakao.bizplatform.common.exception.ApiCommonException;

public class NaviException extends ApiCommonException {
    private final ErrorCode errorCode;

    public NaviException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode() {
        return this.errorCode.getCode();
    }

    public String getMessage() {
        return this.errorCode.getMessage();
    }
}
