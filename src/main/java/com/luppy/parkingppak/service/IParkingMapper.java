package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.elastic.IParkingLot;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IParkingMapper {

    public IParkingLot toIParking(ParkingLot parkingLot) {
        return new IParkingLot(parkingLot.getId(),
                parkingLot.getModificationDate(), parkingLot.getParkingName(), parkingLot.getAddress(),
                parkingLot.getParkingCode(), parkingLot.getType(), parkingLot.getPhoneNumber(), parkingLot.getPayYN()
                , parkingLot.getWeekdayBegin(), parkingLot.getWeekdayEnd(), parkingLot.getWeekendBegin(),
                parkingLot.getWeekendEnd(), parkingLot.getHolidayBegin(), parkingLot.getHolidayEnd(),
                parkingLot.getSyncTime(), parkingLot.getRates(), parkingLot.getTimeRates(), parkingLot.getAddRates(),
                parkingLot.getAddTimeRates(), new GeoPoint(parkingLot.getLat(), parkingLot.getLon()));
    }
}
