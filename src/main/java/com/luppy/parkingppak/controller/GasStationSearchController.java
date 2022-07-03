package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.service.GasStationSearchService;
import com.luppy.parkingppak.service.ParkingLotSearchService;
import com.luppy.parkingppak.utils.GasStationResultQuery;
import com.luppy.parkingppak.utils.ParkingLotResultQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/search/gas")
@RequiredArgsConstructor
public class GasStationSearchController {

    private final GasStationSearchService gasStationSearchService;

    @GetMapping("/query" + "/{query}")
    public ResponseEntity<GasStationResultQuery> searchQuery(@PathVariable String query) throws IOException {
        return new ResponseEntity<>(gasStationSearchService.searchFromQuery(query.trim().toLowerCase()), HttpStatus.OK);
    }
}
