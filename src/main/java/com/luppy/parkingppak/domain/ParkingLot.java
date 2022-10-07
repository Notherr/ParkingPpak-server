package com.luppy.parkingppak.domain;

import com.luppy.parkingppak.init.data.ParkingLotData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime modificationDate;

    // 주차장 이름
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
    private double lat; // 경도
    private double lon; // 위도

    public ParkingLot parkingLotMapper(ParkingLotData.Row row) {
        this.parkingName = row.getPARKING_NAME();
        this.parkingCode = Integer.parseInt(row.getPARKING_CODE());
        this.address = row.getADDR();
        this.type = row.getPARKING_TYPE_NM();
        this.phoneNumber = row.getTEL();
        this.payYN = row.getPAY_YN().equals("Y");
        this.weekdayBegin = row.getWEEKDAY_BEGIN_TIME();
        this.weekdayEnd = row.getWEEKDAY_END_TIME();
        this.weekendBegin = row.getWEEKEND_BEGIN_TIME();
        this.weekendEnd = row.getWEEKEND_END_TIME();
        this.holidayBegin = row.getHOLIDAY_BEGIN_TIME();
        this.holidayEnd = row.getHOLIDAY_END_TIME();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.syncTime = LocalDateTime.parse(row.getSYNC_TIME(),formatter);

        this.rates = (int)Float.parseFloat(row.getRATES());
        this.timeRates = (int)Float.parseFloat(row.getTIME_RATE());
        this.addRates = (int)Float.parseFloat(row.getADD_RATES());
        this.addTimeRates = (int)Float.parseFloat(row.getADD_TIME_RATE());
        this.lon = Double.parseDouble(row.getLNG());
        this.lat = Double.parseDouble(row.getLAT());
        this.modificationDate = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        return this;
    }

}
