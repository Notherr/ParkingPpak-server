package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDto {

    private String email;
    private String name;
    private String jwt;
}
