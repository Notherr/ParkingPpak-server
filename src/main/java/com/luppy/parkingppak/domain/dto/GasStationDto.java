package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GasStationDto {

    private Long id;

    private String compName;
    private String name;
    private String uniqueId;
    private String address;
    private double lon;
    private double lat;
    private int gasolinePrice;
    private int dieselPrice;

    private boolean carWash;
    private boolean cvsExist;
    private String tel;

}
