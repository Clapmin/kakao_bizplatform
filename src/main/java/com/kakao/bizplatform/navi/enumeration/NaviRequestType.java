package com.kakao.bizplatform.navi.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NaviRequestType {
    START("StartNavi"),
    UPDATE("UpdateLocation"),
    CLOSE("Close");

    @JsonValue
    private final String type;

    public static boolean isNaviStart(NaviRequestType type) {
        return START.equals(type);
    }

    public static boolean isNaviUpdate(NaviRequestType type) {
        return UPDATE.equals(type);
    }

    public static boolean isNaviClose(NaviRequestType type) {
        return CLOSE.equals(type);
    }
}
