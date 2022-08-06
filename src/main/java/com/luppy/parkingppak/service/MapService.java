package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.dto.MapRequestDto;
import com.luppy.parkingppak.utils.GasStationResultQuery;
import com.luppy.parkingppak.utils.ParkingLotResultQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchPrefix;
    private final ParkingLotSearchService parkingLotSearchService;
    private final GasStationSearchService gasStationSearchService;

    public List<?> getDataList(MapRequestDto dto) throws IOException {

        // 요청받은 좌표값 기준 범위내 조회 쿼리메소드로 수정 필요.
        if(dto.getType().equals("parking_lot")) {
            ParkingLotResultQuery parkingLotResultQuery = parkingLotSearchService.searchLocationFromQuery(dto.getDistance(),
                    dto.getLat(), dto.getLon());
            return parkingLotResultQuery.getData();

        }else{
            GasStationResultQuery gasStationResultQuery = gasStationSearchService.searchLocationFromQuery(dto.getDistance(),
                    dto.getLat(), dto.getLon());
            return gasStationResultQuery.getData();
        }
    }

//    public Object getData(MapRequestDto dto) {
//        if(dto.getType().equals("parking-lot")){
//            return parkingLotRepository.findById(dto.getId()).orElse(null);
//        }else{
//            return gasStationRepository.findById(dto.getId()).orElse(null);
//        }
//    }
}
