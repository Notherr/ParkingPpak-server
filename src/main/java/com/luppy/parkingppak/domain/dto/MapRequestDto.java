package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapRequestDto {

    // parking_lot, gas_station
    private String type;
    private double lat;
    private double lon;
    private int distance;
    private double searchAfter;
}