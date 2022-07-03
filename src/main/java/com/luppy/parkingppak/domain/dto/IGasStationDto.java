package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IGasStationDto {
    private Long id;

    private String compName;
    private String name;
    private String uniqueId;
    private double xCoor;
    private double yCoor;
    private int gasolinePrice;
    private int dieselPrice;
}
