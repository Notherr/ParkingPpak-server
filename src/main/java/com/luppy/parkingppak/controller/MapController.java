package com.luppy.parkingppak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {


    @GetMapping("/")
    public ResponseEntity<?> getParkingLotList(){

        //좌표값을 받아서
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingLot(@PathVariable String id){
        return ResponseEntity.ok().body("");
    }



}
