package com.kakao.bizplatform.navi.domain;

import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaviHistRepositoryTest {
    @Autowired
    NaviHistRepository histRepository;

    @Test
    @DisplayName("RequestType이 아닌 결과 단건을 반환")
    void findFirstByTransIdAndRequestTypeNotOrderByDateTimeAsc() {
        String transactionId = "kakao-transid-132323";
        NaviHist naviStartHist = NaviHist.builder()
                                    .transId(transactionId)
                                    .dateTime(LocalDateTime.now())
                                    .latitude(1.1)
                                    .longitude(2.2)
                                    .requestType(NaviRequestType.START)
                                    .remainDistance(0)
                                    .build();

        NaviHist naviUpdateHist = NaviHist.builder()
                                         .transId(transactionId)
                                         .dateTime(LocalDateTime.now())
                                         .latitude(1.1)
                                         .longitude(2.2)
                                         .requestType(NaviRequestType.UPDATE)
                                         .remainDistance(0)
                                         .build();

        NaviHist naviCloseHist = NaviHist.builder()
                                          .transId(transactionId)
                                          .dateTime(LocalDateTime.now())
                                          .latitude(1.1)
                                          .longitude(2.2)
                                          .requestType(NaviRequestType.CLOSE)
                                          .remainDistance(0)
                                          .build();

        histRepository.save(naviStartHist);
        histRepository.save(naviUpdateHist);
        histRepository.save(naviCloseHist);

        System.out.println(histRepository.findAll());
        Optional<NaviHist> result = histRepository.findFirstByTransIdAndRequestTypeNotOrderByDateTimeAsc(transactionId, NaviRequestType.START);

        assertThat(result).isNotNull();
        assertThat(result.get().getRequestType()).isEqualTo(NaviRequestType.UPDATE);

    }
}