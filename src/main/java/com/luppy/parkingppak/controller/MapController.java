package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.MapRequestDto;
import com.luppy.parkingppak.service.MapService;
import com.luppy.parkingppak.utils.ResultQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/search/map")
public class MapController {

    private final MapService mapService;

    @GetMapping
    public ResponseEntity<?> getDataList(@RequestHeader(value = "AccountId", required = false) Long accountId,
                                         @RequestParam String type,
                                         @RequestParam double lat,
                                         @RequestParam double lon,
                                         @RequestParam(required = false) Integer distance,
                                         @RequestParam(required = false) Double searchAfter,
                                         @RequestParam(required = false) String keyword) throws IOException {
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

        MapRequestDto mapRequestDto =
                MapRequestDto.builder().type(type).lat(lat).lon(lon).distance(distance).accountId(accountId).build();
        if (searchAfter != null) {
            mapRequestDto.setSearchAfter(searchAfter);
        }
        if (keyword != null && !keyword.isBlank()) {
            mapRequestDto.setKeyword(keyword);
        }

        try {
            ResultQuery dataList = mapService.getDataList(mapRequestDto);
            return ResponseEntity.ok().body(dataList);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/detail")
    public ResponseEntity getDataDetails(@RequestParam String type, @RequestParam Long id) {
        Object data = mapService.getData(type, id);
        return ResponseEntity.ok().body(data);
    }

}
