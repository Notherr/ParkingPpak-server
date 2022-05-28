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

@RestController
@RequestMapping("/api/favorite-list")
@RequiredArgsConstructor
public class FavoriteListController {

    private AccountRepository accountRepository;
    private ParkingLogRepository parkingLogRepository;
    private GasStationRepository gasStationRepository;

    @PostMapping("/")
    public ResponseEntity<?> addFavorite(@RequestBody AddFavoriteRequestDto dto){

        if(dto.getType().equals("parking-lot")){

            Account account = accountRepository.findById(dto.getAccountId()).orElse(null);
            ParkingLot parkingLot = parkingLogRepository.findById(dto.getDataId()).orElse(null);


            if(account == null) return ResponseEntity.badRequest().body("잘못된 계정 id 입니다.");
            if(parkingLot == null) return ResponseEntity.badRequest().body("잘못된 주차장 id 입니다.");

            List<ParkingLot> list = account.getParkingLotList();
            list.add(parkingLot);
        }else if(dto.getType().equals("gas-station")){

            Account account = accountRepository.findById(dto.getAccountId()).orElse(null);
            GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);


            if(account == null) return ResponseEntity.badRequest().body("잘못된 계정 id 입니다.");
            if(gasStation == null) return ResponseEntity.badRequest().body("잘못된 주유소 id 입니다.");

            List<GasStation> list = account.getGasStationList();
            list.add(gasStation);

        }else return ResponseEntity.badRequest().body("잘못된 데이터 타입입니다. parking-lot / gas-station .");

        return ResponseEntity.ok().body("정상적으로 추가 되었습니다.");
    }

    // 수정, 삭제, 조회

}

