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
        private String POLL_DIV_CD;
        private String OS_NM;
        private String PRICE;
        private String DISTANCE;
        private String GIS_X_COOR;
        private String GIS_Y_COOR;
    }

    @Getter @Setter
    class GasStationDataResult {
        private int status;
        private GasStationDataResponse response;
        private String message;
    }
}
