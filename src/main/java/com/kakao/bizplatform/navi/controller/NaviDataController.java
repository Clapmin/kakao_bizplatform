package com.kakao.bizplatform.navi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.bizplatform.common.dto.CommonApiResponse;
import com.kakao.bizplatform.navi.dto.NaviRequestDto;
import com.kakao.bizplatform.navi.service.NaviService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
public class NaviDataController {
    private final NaviService naviService;

    public NaviDataController(NaviService naviService) {
        this.naviService = naviService;
    }

    @GetMapping("/navi/data")
    public ResponseEntity<CommonApiResponse> insertData() throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/navidata.json");
        ObjectMapper mapper = new ObjectMapper();
        List<NaviRequestDto> naviRequestDtoList = mapper.readValue(fileReader, new TypeReference<List<NaviRequestDto>>() {});

        naviRequestDtoList.forEach(naviService::changeNaviStatus);

        return ResponseEntity.ok(CommonApiResponse.successResponse());
    }
}
