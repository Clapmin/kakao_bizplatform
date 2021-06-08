package com.kakao.bizplatform.navi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Point {
    private Double latitude;
    private Double longitude;

    @Builder
    public Point(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
