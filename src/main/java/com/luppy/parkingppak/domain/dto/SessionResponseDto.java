package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SessionResponseDto {

    private String accessToken;
}
