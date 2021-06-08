package com.kakao.bizplatform.navi.service;

import com.kakao.bizplatform.common.enumeration.ErrorCode;
import com.kakao.bizplatform.navi.domain.NaviHist;
import com.kakao.bizplatform.navi.domain.NaviHistRepository;
import com.kakao.bizplatform.navi.domain.NaviResult;
import com.kakao.bizplatform.navi.domain.NaviResultRepository;
import com.kakao.bizplatform.navi.dto.LocationDto;
import com.kakao.bizplatform.navi.dto.NaviRequestDto;
import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import com.kakao.bizplatform.navi.exception.NaviException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaviServiceTest {
    @Autowired
    NaviService naviService;

    @Autowired
    NaviHistRepository naviHistRepository;

    @Autowired
    NaviResultRepository naviResultRepository;

    @BeforeEach
    void beforeEach() {
        naviHistRepository.deleteAll();
        naviResultRepository.deleteAll();
    }

    @Test
    @DisplayName("이미 종료된 경우 NaviException, ErrorCode.NAVI_CLOSED")
    void updateNavi_NaviException() {
        String transactionId = "transactionId";
        LocationDto locationDto = LocationDto.builder()
                                       .transId(transactionId)
                                       .dateTime("202011131101020003")
                                       .totalDistance(7000)
                                       .startName("성남시청")
                                       .goalName("카카오모빌리티")
                                       .lng(127.5678)
                                       .lat(36.9577)
                                       .build();

        NaviRequestDto naviRequestDto = NaviRequestDto.builder()
                                                      .requestType(NaviRequestType.UPDATE)
                                                      .location(locationDto)
                                                      .build();

        NaviHist closeHist = NaviHist.builder()
                                     .transId(transactionId)
                                     .dateTime(LocalDateTime.now())
                                     .latitude(1.1)
                                     .longitude(1.1)
                                     .requestType(NaviRequestType.CLOSE)
                                     .remainDistance(100)
                                     .build();

        naviHistRepository.save(closeHist);

        try {
            naviService.updateNavi(naviRequestDto);
        } catch (NaviException e) {
            assertThat(e.getCode()).isEqualTo(ErrorCode.NAVI_CLOSED.getCode());
        }
    }

    @Test
    @DisplayName("update 텀이 1시간을 넘은경우")
    void updateNavi_isMoreThanAHourAfterLastUpdate() {
        String transactionId = "transactionId";
        LocationDto locationDto = LocationDto.builder()
                                             .transId(transactionId)
                                             .dateTime("202011131101020003")
                                             .totalDistance(7000)
                                             .startName("성남시청")
                                             .goalName("카카오모빌리티")
                                             .lng(127.5678)
                                             .lat(36.9577)
                                             .build();

        NaviRequestDto updateReq1 = NaviRequestDto.builder()
                                                      .requestType(NaviRequestType.UPDATE)
                                                      .location(locationDto)
                                                      .build();

        LocationDto locationDto2 = LocationDto.builder()
                                             .transId(transactionId)
                                             .dateTime("202011131501020003")
                                             .totalDistance(7000)
                                             .startName("성남시청")
                                             .goalName("카카오모빌리티")
                                             .lng(127.5678)
                                             .lat(36.9577)
                                             .build();

        NaviRequestDto updateReq2 = NaviRequestDto.builder()
                                                  .requestType(NaviRequestType.UPDATE)
                                                  .location(locationDto2)
                                                  .build();

        naviService.updateNavi(updateReq1);
        naviService.updateNavi(updateReq2);

        assertThat(naviResultRepository.findAll()).isEmpty();
    }
}