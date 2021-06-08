package com.kakao.bizplatform.navi.domain;

import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NaviHistRepository extends JpaRepository<NaviHist, Long> {
    Optional<NaviHist> findFirstByTransIdAndRequestTypeOrderByDateTimeDesc(String transId, NaviRequestType requestType);

    Optional<NaviHist> findFirstByTransIdAndRequestTypeNotOrderByDateTimeAsc(String transId, NaviRequestType requestType);

    List<NaviHist> findByTransId(String transId);

    @Query("SELECT h FROM NaviHist h WHERE h.transId=:transId AND h.requestType IN (:requestTypes) ORDER BY h.dateTime ASC")
    List<NaviHist> findByTransIdAndRequestTypeIn(@Param("transId") String transId, @Param("requestTypes") List<NaviRequestType> requestTypes);
}
