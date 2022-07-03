package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class OauthKeyDto {

    private String clientId;
    private String clientSecret;
}
