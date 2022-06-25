package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.dto.MapRequestDto;
import com.luppy.parkingppak.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {

    private final MapService mapService;

    @GetMapping("/")
    public ResponseEntity<?> getParkingLotList(@RequestBody MapRequestDto dto){
        /*
         * 좌표를 받아서 해당 좌표의 범위내 데이터 반환.
         */
        List<ParkingLot> parkingLotList = mapService.getParkingLotList(dto);
        return ResponseEntity.ok().body(parkingLotList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingLot(@PathVariable String id){
        return ResponseEntity.ok().body("");
    }



}
