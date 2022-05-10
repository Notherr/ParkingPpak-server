package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import com.luppy.parkingppak.init.data.ParkingLotData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotService implements PublicDataService{

    private final ParkingLotRepository parkingLotRepository;

    @Override
    public void registerData(Object o) {

    }

    @Override
    public Object searchData(Long id) {
        return null;
    }

    @Override
    public Object updateData(Long id, Object o) {
        return null;
    }

    public boolean isEmpty() {
        return parkingLotRepository.findAll().size() == 0;
    }

    public void registerResponseData(ParkingLotData.ParkingLotDataResponse parkingLotDataResponse) throws NullPointerException{
        if (!parkingLotDataResponse.getGetParkInfo().getRESULT().getCODE().equals("INFO-000")) {
            log.error("ResponseData 의 RESULT CODE 에러" + parkingLotDataResponse.getGetParkInfo().getRESULT().getCODE() +  "\n" + parkingLotDataResponse.getGetParkInfo().getRESULT().getMESSAGE());
            return;
        }
        List<ParkingLot> parkingLots = new ArrayList<>();
        for (ParkingLotData.Row row : parkingLotDataResponse.getGetParkInfo().getRow()) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.parkingLotMapper(row);
            parkingLots.add(parkingLot);
        }
        parkingLotRepository.saveAll(parkingLots);
    }
}
