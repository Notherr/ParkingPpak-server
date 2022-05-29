package com.luppy.parkingppak.domain.dto;

import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDto {

    private String email;
    private String name;
    private String jwt;
    private Card card;
    private OilType oilType;
    private NaviType naviType;
}
