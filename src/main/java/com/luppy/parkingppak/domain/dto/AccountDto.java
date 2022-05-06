package com.luppy.parkingppak.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {

    @Schema(hidden = true)
    private Long id;

    @Schema(example = "1234@google.com")
    private String email;

    @Schema(example = "1234")
    private String password;

    @Schema(example = "seongkyulim")
    private String name;

    // private String oilType;
    // private String card;

    @Schema(hidden = true)
    private Boolean verified;
}
