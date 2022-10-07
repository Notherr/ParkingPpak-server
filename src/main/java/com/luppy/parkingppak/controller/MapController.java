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

    @GetMapping
    public ResponseEntity<?> getDataList(@RequestParam String type, @RequestParam double lat,
                                         @RequestParam double lon, @RequestParam(required = false) Integer distance) throws IOException {
        /*
         * 좌표를 받아서 해당 좌표의 범위내 데이터 반환.
         */
        if (distance == null) {
            distance = 5;
        } else {
            if (distance > 20) {
                distance = 20;
            }
        }
        MapRequestDto mapRequestDto = MapRequestDto.builder().type(type).lat(lat).lon(lon).distance(distance).build();
        List<?> dataList = mapService.getDataList(mapRequestDto);
        return ResponseEntity.ok().body(dataList);
    }

    @GetMapping("/detail")
    public ResponseEntity getDataDetails(@RequestParam String type, @RequestParam Long id) {
        Object data = mapService.getData(type, id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

}
