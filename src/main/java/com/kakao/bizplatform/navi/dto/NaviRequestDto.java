package com.kakao.bizplatform.navi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.kakao.bizplatform.navi.domain.NaviHist;
import com.kakao.bizplatform.navi.domain.NaviResult;
import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collections;

@Getter
@NoArgsConstructor
@ToString
public class NaviRequestDto {
    @NotNull
    @JsonProperty("type")
    private NaviRequestType requestType;
    @NotNull
    private LocationDto location;

    private final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss")
                                                                              .appendValue(ChronoField.MILLI_OF_SECOND, 4)
                                                                              .toFormatter();

    @Builder
    public NaviRequestDto(NaviRequestType requestType, LocationDto location) {
        this.requestType = requestType;
        this.location = location;
    }

    public boolean isNaviStart() {
        return NaviRequestType.isNaviStart(requestType);
    }

    public boolean isNaviUpdate() {
        return NaviRequestType.isNaviUpdate(requestType);
    }

    public boolean isNaviClose() {
        return NaviRequestType.isNaviClose(requestType);
    }

    public NaviResult toNaviResultForStart() {
        return NaviResult.builder()
                         .transactionId(location.getTransId())
                         .departureTime(toLocalDateTime(location.getDateTime()))
                         .totalDistance(location.getTotalDistance())
                         .startName(location.getStartName())
                         .goalName(location.getGoalName())
                         .points(Collections.singletonList(location.getPoint()))
                         .build();
    }

    public NaviHist toNaviHist() {
        return NaviHist.builder()
                       .transId(location.getTransId())
                       .dateTime(toLocalDateTime(location.getDateTime()))
                       .latitude(location.getLat())
                       .longitude(location.getLng())
                       .requestType(getRequestType())
                       .remainDistance(isNaviStart() ?  location.getTotalDistance() : location.getRemainDistance())
                       .build();
    }

    private LocalDateTime toLocalDateTime(String dateTime) {
        return (LocalDateTime.parse(dateTime, FORMATTER));
    }
}