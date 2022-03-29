package com.luppy.parkingppak.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private int basicTime;
    private int basicCharge;
    private int addUnitCharge;
    private int addUnitTime;
    //일일 주차권 적용 시간.
    private String dayTicketTime;
    // 일일 주차권 요금.
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
