package com.luppy.parkingppak.init.batch;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ParkingLotCustomWriter implements ItemWriter<ParkingLot> {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Override
    public void write(List<? extends ParkingLot> list) throws Exception {
        for (ParkingLot data : list) {
            log.info("CustomWriter :: Writing data :: " + data.getId() + " " + data.getParkingName() + " " + data.getAddress());
            parkingLotRepository.save(data);
        }
    }
}
