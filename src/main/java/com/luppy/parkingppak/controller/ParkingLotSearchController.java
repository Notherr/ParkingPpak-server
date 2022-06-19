package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.service.ParkingLotSearchService;
import com.luppy.parkingppak.utils.ResultQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class ParkingLotSearchController {

    private final ParkingLotSearchService parkingLotSearchService;

    @GetMapping("/query" + "/{query}")
    public ResponseEntity<ResultQuery> searchQuery(@PathVariable String query) throws IOException {
        return new ResponseEntity<>(parkingLotSearchService.searchFromQuery(query.trim().toLowerCase()), HttpStatus.OK);
    }
}
