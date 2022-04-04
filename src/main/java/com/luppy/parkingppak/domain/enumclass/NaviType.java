package com.luppy.parkingppak.domain.enumclass;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NaviType {

    KAKAONAVI("카카오내비"),
    NAVER("네이버지도"),
    TMAP("티맵"),
    KAKAOMAP("카카오맵"),
    GOOGLE("구글지도");


    private final String title;
}
