package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.MapRequestDto;
import com.luppy.parkingppak.service.MapService;
import com.luppy.parkingppak.utils.ResultQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/search/map")
public class MapController {

    private final MapService mapService;

    @GetMapping
    public ResponseEntity<?> getDataList(@RequestParam String type, @RequestParam double lat,
                                         @RequestParam double lon, @RequestParam(required = false) Integer distance,
                                         @RequestParam(required = false) Double searchAfter) throws IOException {
        /*
         * 좌표를 받아서 해당 좌표의 범위내 데이터 반환.
         */
        if (distance == null) {
            distance = 5;
        }
        if (distance > 20) {
            distance = 20;
        }
        if (!type.equals("parking_lot") && (!type.equals("gas_station"))) {
            return ResponseEntity.badRequest().body("wrong type given. type is parking_lot or gas_station");
        }

        MapRequestDto mapRequestDto = MapRequestDto.builder().type(type).lat(lat).lon(lon).distance(distance).build();
        if (searchAfter != null) {
            mapRequestDto.setSearchAfter(searchAfter);
        }
        ResultQuery dataList = mapService.getDataList(mapRequestDto);
        return ResponseEntity.ok().body(dataList);
    }

    @GetMapping("/detail")
    public ResponseEntity getDataDetails(@RequestParam String type, @RequestParam Long id) {
        Object data = mapService.getData(type, id);
        return ResponseEntity.ok().body(data);
    }

}
