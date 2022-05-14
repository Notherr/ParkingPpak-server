package com.luppy.parkingppak.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecommendedParkingLotDto {

    private Long id;
    private String type;
    private int basicTime;
    private int basicCharge;
    private int addUnitCharge;
    private int addUnitTime;
    private String dayTicketTime;
    private int dayTicket;
    private double xCoor;
    private double yCoor;
    private String phoneNumber;
    private String openDay;
    private String wkdayOperOpenHM;
    private String wkdayOperCloseHM;
    private String satOperOpenHM;
    private String satOperCloseHM;
    private String holOperOpenHM;
    private String holOperCloseHM;
}
