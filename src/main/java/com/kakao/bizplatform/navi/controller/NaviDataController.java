package com.kakao.bizplatform.navi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.bizplatform.common.dto.CommonApiResponse;
import com.kakao.bizplatform.navi.domain.NaviHistRepository;
import com.kakao.bizplatform.navi.domain.NaviResultRepository;
import com.kakao.bizplatform.navi.dto.NaviRequestDto;
import com.kakao.bizplatform.navi.service.NaviService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
public class NaviDataController {
    private final NaviService naviService;
    private final NaviResultRepository naviResultRepository;
    private final NaviHistRepository naviHistRepository;

    public NaviDataController(NaviService naviService, NaviResultRepository naviResultRepository, NaviHistRepository naviHistRepository) {
        this.naviService = naviService;
        this.naviResultRepository = naviResultRepository;
        this.naviHistRepository = naviHistRepository;
    }

    @GetMapping("/navi/data")
    public ResponseEntity<CommonApiResponse> insertData() throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/navidata.json");
        ObjectMapper mapper = new ObjectMapper();
        List<NaviRequestDto> naviRequestDtoList = mapper.readValue(fileReader, new TypeReference<List<NaviRequestDto>>() {});

        naviRequestDtoList.forEach(naviService::changeNaviStatus);

        return ResponseEntity.ok(CommonApiResponse.successResponse());
    }

    @GetMapping("/navi/data/delete")
    public ResponseEntity<CommonApiResponse> deleteAllData() throws IOException {
        naviResultRepository.deleteAll();
        naviHistRepository.deleteAll();
        return ResponseEntity.ok(CommonApiResponse.successResponse());
    }
}
