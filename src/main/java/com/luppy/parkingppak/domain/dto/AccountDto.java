package com.luppy.parkingppak.domain.dto;

import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private Card card;

    private OilType oilType;

    private NaviType naviType;

    private Boolean verified;
}
