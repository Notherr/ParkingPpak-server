package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.GasStation;
import com.luppy.parkingppak.domain.GasStationRepository;
import com.luppy.parkingppak.domain.ParkingLot;
import com.luppy.parkingppak.domain.ParkingLotRepository;
import com.luppy.parkingppak.domain.elastic.IGasStationRepository;
import com.luppy.parkingppak.domain.elastic.IParkingLotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticSynchronizer {

    private final IParkingLotRepository iParkingLotRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final IParkingMapper iParkingMapper;

    private final GasStationRepository gasStationRepository;
    private final IGasStationRepository iGasStationRepository;
    private final IGasStationMapper iGasStationMapper;

    @Scheduled(cron = "0 */3 * * * *")
    @Transactional
    public void sync() {
        log.info("Start Syncing Parking Lot - {}", LocalDateTime.now());
        this.syncParkingLots();
        log.info("End Syncing Parking Lot - {}", LocalDateTime.now());
        log.info("Start Syncing Gas Station - {}", LocalDateTime.now());
        this.syncGasStation();
        log.info("End Syncing Gas Station - {}", LocalDateTime.now());
    }

    private void syncParkingLots() {

        Specification<ParkingLot> parkingLotSpecification =
                ((root, query, criteriaBuilder) -> getModificationDatePredicate(criteriaBuilder, root));
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        if (iParkingLotRepository.count() == 0) {
            for (ParkingLot parkingLot : parkingLots) {
//                log.info("Syncing ParkingLot - {}", parkingLot.getParkingName());
                iParkingLotRepository.save(this.iParkingMapper.toIParking(parkingLot));
            }
        }

    }

    private void syncGasStation() {
        List<GasStation> gasStations = gasStationRepository.findAll();
        if (iGasStationRepository.count() == 0) {
            for (GasStation gasStation : gasStations) {
                iGasStationRepository.save(this.iGasStationMapper.toIParking(gasStation));
            }
        }

    }

    private Predicate getModificationDatePredicate(CriteriaBuilder criteriaBuilder, Root<ParkingLot> root) {
        Expression<Timestamp> currentTime;
        currentTime = criteriaBuilder.currentTimestamp();
        Expression<Timestamp> currentTimeMinus =
                criteriaBuilder.literal(new Timestamp((System.currentTimeMillis() - (1_800_000))));
        return criteriaBuilder.between(root.<Date>get("modificationDate"), currentTimeMinus, currentTime);
    }
}
