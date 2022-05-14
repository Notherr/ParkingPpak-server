package com.luppy.parkingppak.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Schema(example = "96x60812@gmail.com")
    private String email;

    @Schema(example = "1234")
    private String password;
}
