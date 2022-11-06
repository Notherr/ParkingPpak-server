package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.*;
import com.luppy.parkingppak.domain.dto.FavoriteRequestDto;
import com.luppy.parkingppak.domain.dto.GasStationDto;
import com.luppy.parkingppak.domain.dto.ParkingLotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteListService {

    private final AccountRepository accountRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final GasStationRepository gasStationRepository;

    public List<ParkingLotDto> addParkingLotFavorite(String accountId, FavoriteRequestDto dto){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        ParkingLot parkingLot = parkingLotRepository.findById(dto.getDataId()).orElse(null);

        if(account == null || parkingLot == null || account.getParkingLotSet().contains(parkingLot)) return null;

        account.getParkingLotSet().add(parkingLot);
        accountRepository.save(account);

        Set<ParkingLot> parkingLotSet = account.getParkingLotSet();
        parkingLotSet.add(parkingLot);

        return parkingLotSet.stream().map(ParkingLot::entityToDto).collect(Collectors.toList());
    }

    public List<GasStationDto> addGasStationFavorite(String accountId, FavoriteRequestDto dto){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);

        if(account == null || gasStation == null || account.getGasStationSet().contains(gasStation)) return null;

        account.getGasStationSet().add(gasStation);
        accountRepository.save(account);

        Set<GasStation> gasStationSet = account.getGasStationSet();
        gasStationSet.add(gasStation);

        return gasStationSet.stream().map(GasStation::entityToDto).collect(Collectors.toList());

    }

    public List<ParkingLotDto> findParkingLotFavoriteList(String accountId){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        return account.getParkingLotSet().stream().map(ParkingLot::entityToDto).collect(Collectors.toList());
    }

    public List<GasStationDto> findGasStationFavoriteList(String accountId){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        return account.getGasStationSet().stream().map(GasStation::entityToDto).collect(Collectors.toList());
    }

    public String removeFavorite(String accountId, FavoriteRequestDto dto){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);

        if(dto.getType().equals("gas-station")){
            GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);
            if(gasStation == null) return null;
            account.getGasStationSet().remove(gasStation);
            accountRepository.save(account);
            return "ok";
        }else if(dto.getType().equals("parking-lot")) {
            ParkingLot parkingLot = parkingLotRepository.findById(dto.getDataId()).orElse(null);
            if(parkingLot == null) return null;
            account.getParkingLotSet().remove(parkingLot);
            accountRepository.save(account);
            return "ok";
        }else return null;
    }
}
