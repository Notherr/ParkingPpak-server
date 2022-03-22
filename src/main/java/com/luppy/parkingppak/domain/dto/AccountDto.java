package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String oilType;
    private String card;
    private Boolean verified;
}
