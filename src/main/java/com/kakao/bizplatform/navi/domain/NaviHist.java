package com.kakao.bizplatform.navi.domain;

import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class NaviHist extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transId;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private Integer movedDistance = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NaviRequestType requestType;

    @Column(nullable = false)
    private Integer remainDistance;

    public void setMovedDistance(Integer movedDistance) {
        this.movedDistance = movedDistance;
    }

    @Builder
    public NaviHist(Long id, String transId, LocalDateTime dateTime, Double latitude, Double longitude, NaviRequestType requestType, Integer remainDistance) {
        this.id = id;
        this.transId = transId;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestType = requestType;
        this.remainDistance = remainDistance;
    }
}
