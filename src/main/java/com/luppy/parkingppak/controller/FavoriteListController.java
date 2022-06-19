package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.*;
import com.luppy.parkingppak.domain.dto.FavoriteRequestDto;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/favorite-list")
@RequiredArgsConstructor
public class FavoriteListController {

    private final AccountRepository accountRepository;
    private final ParkingLogRepository parkingLogRepository;
    private final GasStationRepository gasStationRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<?> addFavorite(@RequestHeader("Authorization") String jwt, @RequestBody FavoriteRequestDto dto){

        String jwtToken = jwt.replace("Bearer ", "");

        if(dto.getType().equals("parking-lot")){

            Account account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken))).orElse(null);
            ParkingLot parkingLot = parkingLogRepository.findById(dto.getDataId()).orElse(null);


            if(account == null) return ResponseEntity.badRequest().body("잘못된 계정 id 입니다.");
            if(parkingLot == null) return ResponseEntity.badRequest().body("잘못된 주차장 id 입니다.");

            List<ParkingLot> list = account.getParkingLotList();
            list.add(parkingLot);
        }else if(dto.getType().equals("gas-station")){

            Account account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken))).orElse(null);
            GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);


            if(account == null) return ResponseEntity.badRequest().body("잘못된 계정 id 입니다.");
            if(gasStation == null) return ResponseEntity.badRequest().body("잘못된 주유소 id 입니다.");

            List<GasStation> list = account.getGasStationList();
            list.add(gasStation);

        }else return ResponseEntity.badRequest().body("잘못된 데이터 타입입니다. parking-lot / gas-station .");

        return ResponseEntity.ok().body("정상적으로 추가 되었습니다.");
    }

    @GetMapping("/{dataType}")
    public ResponseEntity<?> getFavoriteList(@RequestHeader("Authorization") String jwt, @PathVariable String dataType){
        String jwtToken = jwt.replace("Bearer ", "");

        Account account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken))).orElse(null);

        if(account != null){
            if(dataType.equals("parking-lot")) {
                return ResponseEntity.ok().body(account.getParkingLotList());
            }else if(dataType.equals("gas-station")){
                return ResponseEntity.ok().body(account.getGasStationList());
            }else{
                return ResponseEntity.ok().body("잘못된 데이터 타입입니다.");
            }
        }else{
            return ResponseEntity.ok().body("잘못된 토큰입니다.");
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteFavorite(@RequestHeader("Authorization") String jwt, @RequestBody FavoriteRequestDto dto){
        String jwtToken = jwt.replace("Bearer ", "");

        Account account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken))).orElse(null);

        if(account!=null){
            if(dto.getType().equals("gas-station")){
                GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);
                if(gasStation == null) return ResponseEntity.badRequest().body("잘못된 주유소 id 입니다.");
                account.getGasStationList().remove(gasStation);
                accountRepository.save(account);
                return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
            }else if(dto.getType().equals("parking-lot")) {
                ParkingLot parkingLot = parkingLogRepository.findById(dto.getDataId()).orElse(null);
                if(parkingLot == null) return ResponseEntity.badRequest().body("잘못된 주차장 id 입니다.");
                account.getParkingLotList().remove(parkingLot);
                accountRepository.save(account);
                return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
            }else return ResponseEntity.badRequest().body("잘못된 데이터 타입입니다.");
        }else return ResponseEntity.badRequest().body("잘못된 토큰입니다.");
    }
}

