package com.kakao.bizplatform.navi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LocationDto {
    private String transId;
    private String dateTime;
    private int totalDistance;
    private int remainDistance;
    private String startName;
    private String goalName;
    private double lat;
    private double lng;

    @Builder
    public LocationDto(String transId, String dateTime, int totalDistance, int remainDistance, String startName, String goalName, double lat, double lng) {
        this.transId = transId;
        this.dateTime = dateTime;
        this.totalDistance = totalDistance;
        this.remainDistance = remainDistance;
        this.startName = startName;
        this.goalName = goalName;
        this.lat = lat;
        this.lng = lng;
    }

    public Point getPoint() {
        return new Point(this.lat, lng);
    }

}
