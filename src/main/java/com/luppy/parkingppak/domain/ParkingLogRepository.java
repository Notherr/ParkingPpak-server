package com.luppy.parkingppak.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLogRepository extends JpaRepository<ParkingLot, Long> {
}
