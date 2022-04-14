package com.luppy.parkingppak.domain.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OilType {

    LPG("LPG"),
    GASOLINE("휘발유"),
    VIA("경유"),
    PREMIUM("고급유"),
    ELECTRIC("전기")
    ;


    private final String title;
}
