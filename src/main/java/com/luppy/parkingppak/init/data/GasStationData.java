package com.luppy.parkingppak.init.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.luppy.parkingppak.service.GasStationService;
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
public class GasStationData {

    private final GasStationService gasStationService;

    @PostMapping("/gas/init")
    public ResponseEntity<GasStationDataResult> initGasStation() throws IOException, InterruptedException {
        if (!gasStationService.isEmpty()) {
            log.info("GasStation Data already exists");
            GasStationDataResult dataResult = new GasStationDataResult();
            dataResult.setStatus(404);
            dataResult.setMessage("주유소 정보가 이미 존재합니다");
            return ResponseEntity.ok(dataResult);
        }
        gasStationService.processRegister();
        GasStationDataResult dataResult = new GasStationDataResult();
        dataResult.setStatus(200);
        dataResult.setMessage("주유소 정보 등록 완료");
        return ResponseEntity.ok().body(dataResult);
    }

    @PostMapping("/gas/detail/init")
    public ResponseEntity<GasStationDataResult> initGasStationDetail() {
        gasStationService.addGasStationDetail();
        GasStationDataResult dataResult = new GasStationDataResult();
        dataResult.setStatus(200);
        dataResult.setMessage("주유소 디테일 정보 등록 완료");
        return ResponseEntity.ok().body(dataResult);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class GasStationDataResponse {
        GasStationInfo RESULT;
    }
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class GasStationInfo {
        private List<GasStationData.Row> OIL;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class Row {
        private String UNI_ID;
        private String POLL_DIV_CO;
        private String OS_NM;
        private String PRICE;
        private String DISTANCE;
        private String GIS_X_COOR;
        private String GIS_Y_COOR;
    }
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class GasStationDetailResponse {
        GasStationDetailInfo RESULT;
    }
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class GasStationDetailInfo {
        private List<GasStationData.DetailRow> OIL;
    }

    @Getter @Setter
    class GasStationDataResult {
        private int status;
        private GasStationDataResponse response;
        private String message;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @Getter
    public class DetailRow {
        private String UNI_ID;
        private String POLL_DIV_CO;
        private String GPOLL_DIV_CO;
        private String OS_NM;
        private String VAN_ADR;
        private String NEW_ADR;
        private String TEL;
        private String SIGUNCD;
        private String LPG_YN; // 업종 구분 (N: 주유소, Y:충전소, C: 겸업)
        private String MAINT_YN; // 경정비 시설 유무
        private String CAR_WASH_YN; // 세차장 유무
        private String KPETRO_YN; // 품질인증주유소
        private String CVS_YN; // 편의점 존재 유무
        private String GIS_X_COOR;
        private String GIS_Y_COOR;
        private List<OILPRICE> OIL_PRICE;

        private class OILPRICE {
            private String PRODCD;
            private int PRICE;
            private String TRADE_DT;
            private String TRADE_TM;
        }
    }

}
