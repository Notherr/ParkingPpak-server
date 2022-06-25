package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapRequestDto {

    private double xcoor;
    private double ycoor;

}
