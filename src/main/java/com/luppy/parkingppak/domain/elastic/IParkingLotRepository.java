package com.luppy.parkingppak.domain.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface IParkingLotRepository extends ElasticsearchRepository<IParkingLot, Long> {

//    List<IParkingLot> findByParkingNameContaining(String parkingName);
}
