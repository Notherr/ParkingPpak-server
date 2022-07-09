package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OauthDto {

    private String email;
    private String name;
    private String provider;
}
