package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.GasStationRepository;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import com.luppy.parkingppak.domain.dto.MapRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService {

    private final ParkingLotRepository parkingLotRepository;
    private final GasStationRepository gasStationRepository;

    public List<?> getDataList(MapRequestDto dto){

        // 요청받은 좌표값 기준 범위내 조회 쿼리메소드로 수정 필요.
        if(dto.getType().equals("parking-lot")) {
            return parkingLotRepository.findAll();
        }else{
            return gasStationRepository.findAll();
        }
    }

    public Object getData(MapRequestDto dto) {
        if(dto.getType().equals("parking-lot")){
            return parkingLotRepository.findById(dto.getId()).orElse(null);
        }else{
            return gasStationRepository.findById(dto.getId()).orElse(null);
        }
    }
}
