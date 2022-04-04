package com.luppy.parkingppak.domain.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardCompType {

    SINHAN1("신한카드 Mr.Life"),
    SINHAN2("신한카드 Deep Dream"),
    SINHAN3("신한카드 Deep Dream(모베러웍스)"),
    SINHAN4("신한카드 Deep Oil"),
    SAMSUNG("삼성카드 MILEAGE PLATINUM(스카이패스)"),
    HUYNDAI1("현대카드 Z Family"),
    HUYNDAI2("현대카드 Zero Edition2(포인트형)"),
    HUYNDAI3("현대카드 M BOOST"),
    KB("KB국민카드 탄탄대로 올쇼핑 티타늄카드")
    ;
    private final String title;
}
