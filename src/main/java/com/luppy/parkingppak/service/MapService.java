package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.*;
import com.luppy.parkingppak.domain.dto.IGasStationDto;
import com.luppy.parkingppak.domain.dto.IParkingLotDto;
import com.luppy.parkingppak.domain.dto.MapRequestDto;
import com.luppy.parkingppak.utils.ResultQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class MapService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchPrefix;
    private final ParkingLotSearchService parkingLotSearchService;
    private final ParkingLotRepository parkingLotRepository;
    private final GasStationSearchService gasStationSearchService;
    private final GasStationRepository gasStationRepository;
    private final AccountRepository accountRepository;

    public ResultQuery getDataList(MapRequestDto dto) throws IOException {
        // TODO 함수 너무 dirty, polishing later

        ResultQuery resultQuery;
        // 요청받은 좌표값 기준 범위내 조회 쿼리메소드로 수정 필요.
        if (dto.getType().equals("parking_lot")) {
            resultQuery = parkingLotSearchService.searchLocationFromQuery(dto.getDistance(),
                    dto.getLat(), dto.getLon(), dto.getSearchAfter(), dto.getKeyword());
        } else {
            resultQuery = gasStationSearchService.searchLocationFromQuery(dto.getDistance(),
                    dto.getLat(), dto.getLon(), dto.getSearchAfter(), dto.getKeyword());
        }

        if (dto.getAccountId() != null) {
            Optional<Account> optionalAccount = accountRepository.findById(dto.getAccountId());
            Account account = optionalAccount.orElse(null);
            if (account == null) {
                throw new UsernameNotFoundException("MapRequestDto 의 accountId로 account를 찾을 수 없습니다." + dto.getAccountId());
            }
            HashSet<Long> favoritePlaceSet = new HashSet<>();
            if (dto.getType().equals("parking_lot")) {
                Set<ParkingLot> parkingLotSet = account.getParkingLotSet();
                parkingLotSet.forEach(parkingLot -> {favoritePlaceSet.add(parkingLot.getId());});
            } else {
                Set<GasStation> gasStationSet = account.getGasStationSet();
                gasStationSet.forEach(gasStation -> {favoritePlaceSet.add(gasStation.getId());});
            }
            for (Object place : resultQuery.getData()) {
                if (place instanceof IParkingLotDto) {
                    if (favoritePlaceSet.contains(((IParkingLotDto) place).getId())) {
                        ((IParkingLotDto) place).setIsFavorite(Boolean.TRUE);
                    } else {
                        ((IParkingLotDto) place).setIsFavorite(Boolean.FALSE);
                    }
                }
                if (place instanceof IGasStationDto) {
                    if (favoritePlaceSet.contains(((IGasStationDto) place).getId())) {
                        ((IGasStationDto) place).setIsFavorite(Boolean.TRUE);
                    } else {
                        ((IGasStationDto) place).setIsFavorite(Boolean.FALSE);
                    }
                }
            }
        }else{
           for (Object place : resultQuery.getData()){
               if (place instanceof IParkingLotDto){
                   ((IParkingLotDto) place).setIsFavorite(Boolean.FALSE);
               }
               if (place instanceof IGasStationDto) {
                   ((IGasStationDto) place).setIsFavorite(Boolean.FALSE);
               }
           }
        }

        return resultQuery;
    }

    public Object getData(String type, Long id) {
        if (type.equals("parking_lot")) {
            return parkingLotRepository.findById(id).orElse(null);
        } else if (type.equals("gas_station")) {
            return gasStationRepository.findById(id).orElse(null);
        } else {
            throw new ValidationException("given type is not valid");
        }
    }
}
