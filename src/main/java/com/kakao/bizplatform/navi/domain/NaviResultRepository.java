package com.kakao.bizplatform.navi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaviResultRepository extends JpaRepository<NaviResult, Long> {
    Optional<NaviResult> findBytransactionId(String transactionId);
}
