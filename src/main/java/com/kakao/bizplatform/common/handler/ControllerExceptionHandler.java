package com.kakao.bizplatform.common.handler;

import com.kakao.bizplatform.common.dto.CommonApiResponse;
import com.kakao.bizplatform.common.enumeration.ErrorCode;
import com.kakao.bizplatform.common.exception.ApiCommonException;
import com.kakao.bizplatform.navi.exception.NaviException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ApiCommonException.class)
    @ResponseBody
    public CommonApiResponse handleApiCommonException(ApiCommonException e, HttpServletRequest request, HttpServletResponse response) {
        /*....*/
        return CommonApiResponse.failResponse(ErrorCode.API_COMMON_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(NaviException.class)
    @ResponseBody
    public CommonApiResponse handleNaviException(NaviException e, HttpServletRequest request, HttpServletResponse response) {
        /*....*/
        return CommonApiResponse.failResponse(e.getCode(), e.getMessage());
    }
}
