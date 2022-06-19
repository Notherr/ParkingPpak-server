package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.elastic.IParkingLot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class IParkingMapper {

    public IParkingLot toIParking(ParkingLot parkingLot) {
        IParkingLot iParkingLot = new IParkingLot(parkingLot.getId(),
                parkingLot.getModificationDate(), parkingLot.getParkingName(), parkingLot.getAddress(),
                parkingLot.getParkingCode(), parkingLot.getType(), parkingLot.getPhoneNumber(), parkingLot.getPayYN()
                , parkingLot.getWeekdayBegin(), parkingLot.getWeekdayEnd(), parkingLot.getWeekendBegin(),
                parkingLot.getWeekendEnd(), parkingLot.getHolidayBegin(), parkingLot.getHolidayEnd(),
                parkingLot.getSyncTime(), parkingLot.getRates(), parkingLot.getTimeRates(), parkingLot.getAddRates(),
                parkingLot.getAddTimeRates(), parkingLot.getXCoor(), parkingLot.getYCoor());
        return iParkingLot;
    }
}
