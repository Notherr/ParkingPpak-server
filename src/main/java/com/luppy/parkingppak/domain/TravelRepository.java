package com.luppy.parkingppak.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findAllByIdAndParkingLot(Long id, ParkingLot parkingLot);

    List<Travel> findAllByIdAndGasStation(Long id, GasStation gasStation);

}
