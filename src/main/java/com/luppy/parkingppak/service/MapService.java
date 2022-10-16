package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.GasStationRepository;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import com.luppy.parkingppak.domain.dto.MapRequestDto;
import com.luppy.parkingppak.utils.ResultQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class MapService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchPrefix;
    private final ParkingLotSearchService parkingLotSearchService;
    private final ParkingLotRepository parkingLotRepository;
    private final GasStationSearchService gasStationSearchService;
    private final GasStationRepository gasStationRepository;

    public ResultQuery getDataList(MapRequestDto dto) throws IOException {

        // 요청받은 좌표값 기준 범위내 조회 쿼리메소드로 수정 필요.
        if (dto.getType().equals("parking_lot")) {
            return parkingLotSearchService.searchLocationFromQuery(dto.getDistance(),
                    dto.getLat(), dto.getLon(), dto.getSearchAfter());
        } else {
            return gasStationSearchService.searchLocationFromQuery(dto.getDistance(),
                    dto.getLat(), dto.getLon(), dto.getSearchAfter());
        }
    }

    public Object getData(String type,  Long id) {
        if (type.equals("parking_lot")) {
            return parkingLotRepository.findById(id).orElse(null);
        } else if (type.equals("gas_station")) {
            return gasStationRepository.findById(id).orElse(null);
        } else {
            throw new ValidationException("given type is not valid");
        }
    }
}
