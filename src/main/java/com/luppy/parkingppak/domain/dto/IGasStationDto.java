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
    private String address;
    private double lon;
    private double lat;
    private int gasolinePrice;
    private int dieselPrice;
    private boolean carWash;
    private boolean cvsExist;
    private String tel;

    // 검색 좌표에서 떨어진거리 (m)
    private double distance;

    private Boolean isFavorite;
}
