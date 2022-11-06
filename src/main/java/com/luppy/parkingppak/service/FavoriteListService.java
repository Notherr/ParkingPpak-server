package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.*;
import com.luppy.parkingppak.domain.dto.FavoriteRequestDto;
import com.luppy.parkingppak.domain.dto.GasStationDto;
import com.luppy.parkingppak.domain.dto.ParkingLotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if(account == null || parkingLot == null || account.getParkingLotList().contains(parkingLot)) return null;

        account.getParkingLotList().add(parkingLot);
        accountRepository.save(account);

        List<ParkingLot> list = account.getParkingLotList();
        list.add(parkingLot);

        return list.stream().map(ParkingLot::entityToDto).collect(Collectors.toList());
    }

    public List<GasStationDto> addGasStationFavorite(String accountId, FavoriteRequestDto dto){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);

        if(account == null || gasStation == null || account.getGasStationList().contains(gasStation)) return null;

        account.getGasStationList().add(gasStation);
        accountRepository.save(account);

        List<GasStation> list = account.getGasStationList();
        list.add(gasStation);

        return list.stream().map(GasStation::entityToDto).collect(Collectors.toList());

    }

    public List<ParkingLotDto> findParkingLotFavoriteList(String accountId){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        return account.getParkingLotList().stream().map(ParkingLot::entityToDto).collect(Collectors.toList());
    }

    public List<GasStationDto> findGasStationFavoriteList(String accountId){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        return account.getGasStationList().stream().map(GasStation::entityToDto).collect(Collectors.toList());
    }

    public String removeFavorite(String accountId, FavoriteRequestDto dto){
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);

        if(dto.getType().equals("gas-station")){
            GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);
            if(gasStation == null) return null;
            account.getGasStationList().remove(gasStation);
            accountRepository.save(account);
            return "ok";
        }else if(dto.getType().equals("parking-lot")) {
            ParkingLot parkingLot = parkingLotRepository.findById(dto.getDataId()).orElse(null);
            if(parkingLot == null) return null;
            account.getParkingLotList().remove(parkingLot);
            accountRepository.save(account);
            return "ok";
        }else return null;
    }
}
