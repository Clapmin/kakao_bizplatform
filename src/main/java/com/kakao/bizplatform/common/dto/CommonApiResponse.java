package com.kakao.bizplatform.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommonApiResponse {
    private static final String CODE_SUCCESS = "0";
    private static final String MSG_SUCCESS = "success";
    private String code;
    private String message;
    private Object body;

    @Builder
    public CommonApiResponse(String code, String message, Object body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public static CommonApiResponse successResponse() {
        return CommonApiResponse.builder()
                .code(CODE_SUCCESS)
                .message(MSG_SUCCESS)
                .build();
    }

    public static CommonApiResponse successResponse(Object body) {
        return CommonApiResponse.builder()
                                .code(CODE_SUCCESS)
                                .message(MSG_SUCCESS)
                                .body(body)
                                .build();
    }

    public static CommonApiResponse failResponse(String code, String message) {
        return CommonApiResponse.builder()
                                .code(code)
                                .message(message)
                                .build();

    }
}
