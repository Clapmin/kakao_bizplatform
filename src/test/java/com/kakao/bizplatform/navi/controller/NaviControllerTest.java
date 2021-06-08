package com.kakao.bizplatform.navi.controller;

import com.kakao.bizplatform.navi.dto.LocationDto;
import com.kakao.bizplatform.navi.dto.NaviRequestDto;
import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NaviControllerTest {
    @Autowired
    WebTestClient webTestClient;

//    @Test
//    @DisplayName("성공 응답 확인")
//    void changeNaviStatus() {
//        LocationDto locationDto = LocationDto.builder()
//                                       .transId("kakao20201113-0001")
//                                       .dateTime(LocalDateTime.now().toString())
//                                       .totalDistance(7000)
//                                       .startName("성남시청")
//                                       .goalName("카카오모빌리티")
//                                       .lng(127.5678)
//                                       .lat(36.9577)
//                                       .build();
//
//        NaviRequestDto naviRequestDto = NaviRequestDto.builder()
//                                                      .requestType(NaviRequestType.START)
//                                                      .location(locationDto)
//                                                      .build();
//
//        webTestClient.post()
//                     .uri("/navi")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(Mono.just(naviRequestDto), NaviRequestDto.class)
//                     .exchange();
//    }
}