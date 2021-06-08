package com.kakao.bizplatform.navi.domain;

import com.kakao.bizplatform.navi.converter.ListConverter;
import com.kakao.bizplatform.navi.dto.Point;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@ToString(callSuper = true)
public class NaviResult extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private Boolean workApproved = true;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private String startName;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    private Integer totalDistance;

    private Integer movedDistance;

    @Convert(converter = ListConverter.class)
    private List<Point> points; //    추적된 위경도 정보 (latitude, longitude)

    public void setMovedDistance(Integer movedDistance) {
        this.movedDistance = movedDistance;
    }

    public void setWorkApproved(Boolean workApproved) {
        this.workApproved = workApproved;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Builder
    public NaviResult(Long id, String transactionId, LocalDateTime departureTime, LocalDateTime arrivalTime, String startName, String goalName, Integer totalDistance, Integer movedDistance, List<Point> points) {
        this.id = id;
        this.transactionId = transactionId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.startName = startName;
        this.goalName = goalName;
        this.totalDistance = totalDistance;
        this.movedDistance = movedDistance;
        this.points = points;
    }
}
