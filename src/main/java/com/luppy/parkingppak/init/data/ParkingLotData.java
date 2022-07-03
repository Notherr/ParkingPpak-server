package com.luppy.parkingppak.init.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.luppy.parkingppak.service.ParkingLotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/data")
@Slf4j
public class ParkingLotData {

    private final ParkingLotService parkingLotService;

    @PostMapping("/parking/init")
    public ResponseEntity<ParkingLotDataResult> initParkingLot() throws IOException, InterruptedException {
        if (!parkingLotService.isEmpty()) {
            log.info("ParkingLot Data already exists");
            ParkingLotDataResult dataResult = new ParkingLotDataResult();
            dataResult.setStatus(404);
            dataResult.setMessage("주차장 정보가 이미 존재합니다.");
            return ResponseEntity.ok(dataResult);
        }

        parkingLotService.processRegister();

        ParkingLotDataResult dataResult = new ParkingLotDataResult();
        dataResult.setStatus(200);
        dataResult.setMessage("주차장 정보 등록 완료");
        return ResponseEntity.ok().body(dataResult);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class ParkingLotDataResponse {
        ParkingInfo GetParkInfo;
    }
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class ParkingInfo {
        private int list_total_count;
        private Result RESULT;
        private List<Row> row;
    }
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class Result {
        private String CODE;
        private String MESSAGE;
    }
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class Row {
        private String PARKING_NAME;
        private String ADDR;
        private String PARKING_CODE;
        private String PARKING_TYPE;
        private String PARKING_TYPE_NM;
        private String OPERATION_RULE;
        private String OPERATION_RULE_NM;
        private String TEL;
        private String CAPACITY;
        private String PAY_YN;
        private String PAY_NM;
        private String NIGHT_FREE_OPEN;
        private String NIGHT_FREE_OPEN_NM;
        private String WEEKDAY_BEGIN_TIME;
        private String WEEKDAY_END_TIME;
        private String WEEKEND_BEGIN_TIME;
        private String WEEKEND_END_TIME;
        private String HOLIDAY_BEGIN_TIME;
        private String HOLIDAY_END_TIME;
        private String SYNC_TIME;
        private String SATURDAY_PAY_YN;
        private String SATURDAY_PAY_NM;
        private String HOLIDAY_PAY_YN;
        private String HOLIDAY_PAY_NM;
        private String FULLTIME_MONTHLY;
        private String GRP_PARKNM;
        private String RATES;
        private String TIME_RATE;
        private String ADD_RATES;
        private String ADD_TIME_RATE;
        private String BUS_RATES;
        private String BUS_TIME_RATE;
        private String BUS_ADD_TIME_RATE;
        private String BUS_ADD_RATES;
        private String DAY_MAXIMUM;
        private String LAT;
        private String LNG;
    }

    @Getter @Setter
    class ParkingLotDataResult {
        private int status;
        private ParkingLotDataResponse response;
        private String message;
    }

}
