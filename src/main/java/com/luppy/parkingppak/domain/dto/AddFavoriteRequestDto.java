package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddFavoriteRequestDto {

    // parking-lot, gas-station
    private String type;

    private Long dataId;
    private Long accountId;

}
