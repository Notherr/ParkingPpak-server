package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.MapRequestDto;

import com.luppy.parkingppak.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/search/map")
public class MapController {

    private final MapService mapService;

    @GetMapping("/")

    public ResponseEntity<?> getDataList(@RequestBody MapRequestDto dto) throws IOException {
        /*
         * 좌표를 받아서 해당 좌표의 범위내 데이터 반환.
         */
        List<?> dataList = mapService.getDataList(dto);
        return ResponseEntity.ok().body(dataList);
    }

//    @GetMapping("/detail")
//    public ResponseEntity<?> getData(@RequestBody MapRequestDto dto){
//
//        Object data  = mapService.getData(dto);
//        return ResponseEntity.ok().body(data);
//    }

//    @GetMapping("/detail")
//    public ResponseEntity<?> getData(@RequestBody MapRequestDto dto){
//
//        Object data  = mapService.getData(dto);
//        return ResponseEntity.ok().body(Response.GET_MAP_DATA_OK(data));
//    }
}
