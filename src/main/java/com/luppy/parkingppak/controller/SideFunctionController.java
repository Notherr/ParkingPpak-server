package com.luppy.parkingppak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SideFunctionController {


    @PostMapping("/")
    public ResponseEntity<?> addToFavoriteList(){
        // account_parkinglot_list에 데이터 추가.
        // parkinglot인지 gasstation인지
        // parkinglot/gasstation id , 유저 id 값을 받아 값 추가.

        return ResponseEntity.ok().body(null);
    }

}
