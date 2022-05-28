package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.*;
import com.luppy.parkingppak.domain.dto.AddFavoriteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/side-function")
@RequiredArgsConstructor
public class SideFunctionController {

    private AccountRepository accountRepository;
    private ParkingLogRepository parkingLogRepository;
    private GasStationRepository gasStationRepository;


    @PostMapping("/add-favorite")
    public ResponseEntity<?> addFavorite(@RequestBody AddFavoriteRequestDto dto){

        if(dto.getType().equals("parking-lot")){

            Optional<Account> account = accountRepository.findById(dto.getAccountId());
            Optional<ParkingLot> parkingLot = parkingLogRepository.findById(dto.getDataId());


            if(account.isEmpty()) return ResponseEntity.badRequest().body("잘못된 계정 id 입니다.");
            if(parkingLot.isEmpty()) return ResponseEntity.badRequest().body("잘못된 주차장 id 입니다.");

            List<ParkingLot> list = account.get().getParkingLotList();
            list.add(parkingLot);
        }else if(dto.getType().equals("gas-station")){

            Optional<Account> account = accountRepository.findById(dto.getAccountId());
            Optional<GasStation> gasStation = gasStationRepository.findById(dto.getDataId());


            if(account.isEmpty()) return ResponseEntity.badRequest().body("잘못된 계정 id 입니다.");
            if(gasStation.isEmpty()) return ResponseEntity.badRequest().body("잘못된 주유소 id 입니다.");

            List<GasStation> list = account.get().getGasStationList();
            list.add(gasStation);

        }else return ResponseEntity.badRequest().body("잘못된 타입입니다.");

        return ResponseEntity.ok().body("정상적으로 추가 되었습니다.");
    }

}
