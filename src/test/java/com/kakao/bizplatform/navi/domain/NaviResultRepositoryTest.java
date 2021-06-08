package com.kakao.bizplatform.navi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.bizplatform.navi.dto.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class NaviResultRepositoryTest {
    @Autowired
    NaviResultRepository repository;

//    @Test
//    @DisplayName("insert 확인")
//    void insert() {
//        List<Point> pointList = new ArrayList<>();
//        pointList.add(new Point(1.2, 3.4));
//        pointList.add(new Point(5.6, 7.8));
//
//        NaviResult naviResult = NaviResult.builder()
//                                          .transactionId("transactionId1")
//                                          .departureTime(LocalDateTime.now())
//                                          .arrivalTime(LocalDateTime.now())
//                                          .startName("카카오")
//                                          .goalName("모빌리티")
//                                          .totalDistance(1000)
//                                          .movedDistance(1000)
//                                          .points(pointList)
//                                          .build();
//
//        repository.save(naviResult);
//
//        List<NaviResult> resultList = repository.findAll();
//
//        Assertions.assertThat(naviResult.getTransactionId()).isEqualTo(resultList.get(0).getTransactionId());
//    }
}