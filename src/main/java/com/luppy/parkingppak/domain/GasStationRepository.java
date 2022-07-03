package com.luppy.parkingppak.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {

    Optional<GasStation> findByUniqueId(String uniqueId);
}
