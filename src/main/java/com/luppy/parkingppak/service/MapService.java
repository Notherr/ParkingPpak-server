package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import com.luppy.parkingppak.domain.dto.MapRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService {

    private final ParkingLotRepository parkingLotRepository;

    public List<ParkingLot> getParkingLotList(MapRequestDto dto){

        // 요청받은 좌표값 기준 범위내 조회 쿼리메소드로 수정 필요.
        return parkingLotRepository.findAll();
    }

}
