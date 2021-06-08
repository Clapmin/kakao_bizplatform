package com.kakao.bizplatform.navi.service;

import com.kakao.bizplatform.common.enumeration.ErrorCode;
import com.kakao.bizplatform.navi.domain.NaviHist;
import com.kakao.bizplatform.navi.domain.NaviHistRepository;
import com.kakao.bizplatform.navi.domain.NaviResult;
import com.kakao.bizplatform.navi.domain.NaviResultRepository;
import com.kakao.bizplatform.navi.dto.NaviRequestDto;
import com.kakao.bizplatform.navi.dto.Point;
import com.kakao.bizplatform.navi.enumeration.NaviRequestType;
import com.kakao.bizplatform.navi.exception.NaviException;
import com.kakao.bizplatform.navi.util.LocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NaviService {
    private final NaviResultRepository naviResultRepository;
    private final NaviHistRepository naviHistRepository;

    public NaviService(NaviResultRepository naviResultRepository, NaviHistRepository naviHistRepository) {
        this.naviResultRepository = naviResultRepository;
        this.naviHistRepository = naviHistRepository;
    }

    public void changeNaviStatus(NaviRequestDto request) {
        if (request.isNaviStart()) {
            startNavi(request);
        } else if (request.isNaviUpdate()) {
            updateNavi(request);
        } else if (request.isNaviClose()) {
            closeNavi(request);
        }
    }

    public void startNavi(NaviRequestDto request) {
        try {
            naviResultRepository.save(request.toNaviResultForStart());
        } catch (Exception e) {
            throw new NaviException(ErrorCode.NAVI_START_FAIL);
        }

        this.updateNavi(request);
    }

    public void updateNavi(NaviRequestDto request) {
        NaviHist newNaviHist = request.toNaviHist();

        if (naviHistRepository.findFirstByTransIdAndRequestTypeOrderByDateTimeDesc(newNaviHist.getTransId(), NaviRequestType.CLOSE).isPresent()) {
            throw new NaviException(ErrorCode.NAVI_CLOSED);
        }

        try {
            Optional<NaviHist> updatedHistOptional = naviHistRepository.findFirstByTransIdAndRequestTypeOrderByDateTimeDesc(newNaviHist.getTransId(), NaviRequestType.UPDATE);

            if (updatedHistOptional.isPresent()) {
                NaviHist naviHistBefore = updatedHistOptional.get();
                newNaviHist.setMovedDistance(Math.abs(naviHistBefore.getRemainDistance() - newNaviHist.getRemainDistance()));
            }

            naviHistRepository.save(newNaviHist);

            if (isMoreThanAHourAfterLastUpdate(request)) {
                removeNaviResult(newNaviHist);
            }
        } catch (Exception e) {
            throw new NaviException(ErrorCode.NAVI_COMMON);
        }
    }

    private boolean isMoreThanAHourAfterLastUpdate(NaviRequestDto request) {
        NaviHist naviHist = request.toNaviHist();
        Optional<NaviHist> naviHistOptional = naviHistRepository.findFirstByTransIdAndRequestTypeOrderByDateTimeDesc(naviHist.getTransId(), NaviRequestType.UPDATE);

        if (naviHistOptional.isPresent()) {
            Duration between = Duration.between(naviHistOptional.get().getDateTime(), naviHist.getDateTime());
            return between.getSeconds() >= 3600;
        }

        return false;
    }

    public void closeNavi(NaviRequestDto request) {
        try {
            this.updateNavi(request);

            NaviHist naviCloseHist = request.toNaviHist();
            Optional<NaviResult> naviResultOptional = naviResultRepository.findBytransactionId(naviCloseHist.getTransId());

            if (naviResultOptional.isPresent()) {
                NaviResult naviResult = naviResultOptional.get();
                int movedDistanceMeters = getMovedMeters(naviCloseHist.getTransId());
                if (movedDistanceMeters < 500.0 || isFirstUpdateAfter24HoursOfStartingNavi(request)) {
                    naviResultRepository.deleteById(naviResult.getId());
                } else {
                    naviResult.setMovedDistance(movedDistanceMeters);
                    naviResult.setWorkApproved(naviCloseHist.getRemainDistance() < 100);
                    naviResult.setArrivalTime(naviCloseHist.getDateTime());

                    List<NaviHist> naviHistList = naviHistRepository.findByTransIdAndRequestTypeIn(naviCloseHist.getTransId(), Arrays.asList(NaviRequestType.UPDATE, NaviRequestType.CLOSE));
                    List<Point> movedPointList = new ArrayList<>();

                    if (!CollectionUtils.isEmpty(naviHistList)) {
                        naviHistList.forEach(naviHist -> movedPointList.add(new Point(naviHist.getLatitude(), naviHist.getLongitude())));
                    }

                    naviResult.setPoints(movedPointList);
                    naviResultRepository.save(naviResult);
                }
            }
        } catch (Exception e) {
            throw new NaviException(ErrorCode.NAVI_COMMON);
        }
    }

    private int getMovedMeters(String transId) {
        List<NaviHist> histList = naviHistRepository.findByTransId(transId);

        if (CollectionUtils.isEmpty(histList)) {
            return 0;
        }

        return  histList.stream().mapToInt(NaviHist::getMovedDistance).sum();
    }

    private boolean isFirstUpdateAfter24HoursOfStartingNavi(NaviRequestDto request) {
        if (request.isNaviStart()){
            return false;
        }

        NaviHist naviHist = request.toNaviHist();
        Optional<NaviHist> startHistOptional = naviHistRepository.findFirstByTransIdAndRequestTypeOrderByDateTimeDesc(naviHist.getTransId(), NaviRequestType.START);
        Optional<NaviHist> firstUpdateHistOptional = naviHistRepository.findFirstByTransIdAndRequestTypeNotOrderByDateTimeAsc(naviHist.getTransId(), NaviRequestType.START);

        if (startHistOptional.isPresent() && firstUpdateHistOptional.isPresent()) {
            Duration between = Duration.between(startHistOptional.get().getDateTime(), firstUpdateHistOptional.get().getDateTime());
            return between.getSeconds() >= 3600 * 24;
        }

        return true;
    }

    private void removeNaviResult(NaviHist newNaviHist) {
        Optional<NaviResult> naviResultOptional = naviResultRepository.findBytransactionId(newNaviHist.getTransId());
        naviResultOptional.ifPresent(naviResult -> naviResultRepository.deleteById(naviResult.getId()));
    }

    public List<NaviResult> getAllResult() {
        return naviResultRepository.findAll();
    }

    public List<NaviHist> getAllHist() {
        return naviHistRepository.findAll();
    }
}
