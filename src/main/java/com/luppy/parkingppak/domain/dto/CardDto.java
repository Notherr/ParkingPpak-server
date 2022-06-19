package com.luppy.parkingppak.domain.dto;

import com.luppy.parkingppak.domain.enumclass.CardCompName;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
public class CardDto {

    private Long id;
    private String name;
    private CardCompName compName;
    private String content;
}
