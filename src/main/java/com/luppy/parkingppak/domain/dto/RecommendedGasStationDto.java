package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecommendedGasStationDto {

    private Long id;
    private String compName;
    private String name;
    private String uniqueId;
    private double xCoor;
    private double yCoor;
}
