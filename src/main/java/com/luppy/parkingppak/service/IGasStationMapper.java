package com.luppy.parkingppak.service;


import com.luppy.parkingppak.domain.GasStation;
import com.luppy.parkingppak.domain.elastic.IGasStation;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IGasStationMapper {


    public IGasStation toIParking(GasStation gasStation) {
         return new IGasStation(
                gasStation.getId(),
                gasStation.getCompName(),
                gasStation.getName(),
                gasStation.getUniqueId(),
                new GeoPoint(gasStation.getLat(), gasStation.getLon()),
                gasStation.getGasolinePrice(),
                gasStation.getDieselPrice()
        );
    }
}
