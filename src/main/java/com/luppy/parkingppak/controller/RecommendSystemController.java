package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.RecommendedGasStationDto;
import com.luppy.parkingppak.domain.dto.RecommendedParkingLotDto;
import com.luppy.parkingppak.service.RecommendSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class RecommendSystemController {
    private RecommendSystemService recommendSystemService;


    @GetMapping("/accounts/recommend-parkinglot")
    public ResponseEntity<?> recommendParkingLot(@RequestHeader("Authorization") String jwt){
        RecommendedParkingLotDto recommendedParkingLotDto =  recommendSystemService.recommendParkingLot(jwt);
        return ResponseEntity.ok().body(recommendedParkingLotDto);
    }

    @GetMapping("/accounts/recommend-gasstation")
    public ResponseEntity<?> recommendGasStation(@RequestHeader("Authorization") String jwt){
        RecommendedGasStationDto recommendedGasStationDto = recommendSystemService.recommendGasStation(jwt);
        return ResponseEntity.ok().body(recommendedGasStationDto);
    }
}
