package com.luppy.parkingppak.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IParkingLotDto {
    private Long id;
    private String parkingName;
    // 주차장 위치 좌표
    private double lon;
    private double lat;
    private String address;
    private Boolean payYN;
    // 주중 운영 시간
    private String weekdayBegin;
    private String weekdayEnd;
    // 주말 운영 시간
    private String weekendBegin;
    private String weekendEnd;
    // 공휴일 운영 시간
    private String holidayBegin;
    private String holidayEnd;

    // 기본 주차 요금
    private int rates;
    // 기본 주차 시간 (분 단위)
    private int timeRates;
    // 추가 단위 요금
    private int addRates;
    // 추가 단위 시간 (분 단위)
    private int addTimeRates;
}
