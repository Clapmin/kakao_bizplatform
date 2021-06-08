package com.kakao.bizplatform.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    API_COMMON_ERROR("00000", "시스템 오류가 발생했습니다.(관리자에게 문의 바랍니다.)"),


    NAVI_COMMON("00001", "네비 데이터 조회 오류"),
    NAVI_START_FAIL("00002", "네비 시작 실패"),
    NAVI_START_NOT_FOUND("00003", "네비 시작여부 확인불가"),
    NAVI_CLOSED("00004", "네비 종료(목적지 도착)");

    private final String code;
    private final String message;
}
