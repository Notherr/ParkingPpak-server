package com.luppy.parkingppak.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountParkingLotListRepository extends JpaRepository<AccountParkingLotList, Long> {
}
