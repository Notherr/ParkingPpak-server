package com.luppy.parkingppak.domain.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

@Document(indexName = "parking_lot")
@Getter
@AllArgsConstructor @NoArgsConstructor
public class IParkingLot {

    @Id
    private Long id;

    private LocalDateTime modificationDate;

    private String parkingName;

    private String address;
    // 고유 코드
    private int parkingCode;
    // 종류 e.g. 노외 주차장
    private String type;
    // 전화 번호
    private String phoneNumber;
    // 유무료 구분
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
    // 최종 데이터 동기화 시간
    private LocalDateTime syncTime;
    // 기본 주차 요금
    private int rates;
    // 기본 주차 시간 (분 단위)
    private int timeRates;
    // 추가 단위 요금
    private int addRates;
    // 추가 단위 시간 (분 단위)
    private int addTimeRates;
    // 주차장 위치 좌표
    private double xCoor;
    private double yCoor;
}
