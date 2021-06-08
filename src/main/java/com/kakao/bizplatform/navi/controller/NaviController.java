package com.kakao.bizplatform.navi.controller;

import com.kakao.bizplatform.common.dto.CommonApiResponse;
import com.kakao.bizplatform.navi.dto.NaviRequestDto;
import com.kakao.bizplatform.navi.service.NaviService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaviController {
    private final NaviService naviService;

    public NaviController(NaviService naviService) {
        this.naviService = naviService;
    }

    @GetMapping("/navi/result")
    public CommonApiResponse getALlResult() {
        return CommonApiResponse.successResponse(naviService.getAllResult());
    }

    @GetMapping("/navi/hist")
    public CommonApiResponse getAllHist() {
        return CommonApiResponse.successResponse(naviService.getAllHist());
    }

    @PostMapping("/navi")
    public CommonApiResponse changeNaviStatus(@RequestBody NaviRequestDto request) {
        naviService.changeNaviStatus(request);
        return CommonApiResponse.successResponse();
    }
}
