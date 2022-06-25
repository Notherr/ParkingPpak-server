package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapRequestDto {

    // parking-lot, gas-station
    private String type;
    private double xcoor;
    private double ycoor;
    private Long id;
}