package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.*;
import com.luppy.parkingppak.domain.dto.FavoriteRequestDto;
import com.luppy.parkingppak.domain.dto.Response;
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
    private final ParkingLotRepository parkingLotRepository;
    private final GasStationRepository gasStationRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<?> addFavorite(@RequestHeader("AccountId") String accountId, @RequestBody FavoriteRequestDto dto){

        if(dto.getType().equals("parking-lot")){

            Account account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(accountId))).orElse(null);
            ParkingLot parkingLot = parkingLotRepository.findById(dto.getDataId()).orElse(null);


            if(account == null) return ResponseEntity.badRequest().body(Response.INVALID_ACCOUNT_ID(null));
            if(parkingLot == null) return ResponseEntity.badRequest().body(Response.INVALID_PARKINGLOT_ID(null));
            if (account.getParkingLotList().contains(parkingLot)) return ResponseEntity.badRequest().body(Response.REDUNDANT_FAVORITE(null));

            account.getParkingLotList().add(parkingLot);
            accountRepository.save(account);

            List<ParkingLot> list = account.getParkingLotList();
            list.add(parkingLot);

            return ResponseEntity.ok().body(Response.ADD_FAVORITE_OK(list));

        }else if(dto.getType().equals("gas-station")){

            Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
            GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);


            if(account == null) return ResponseEntity.badRequest().body(Response.INVALID_ACCOUNT_ID(null));
            if(gasStation == null) return ResponseEntity.badRequest().body(Response.INVALID_GAS_STATION_ID(null));
            if ( account.getGasStationList().contains(gasStation)) return ResponseEntity.badRequest().body(Response.REDUNDANT_FAVORITE(null));

            account.getGasStationList().add(gasStation);
            accountRepository.save(account);

            List<GasStation> list = account.getGasStationList();
            list.add(gasStation);

            return ResponseEntity.ok().body(Response.ADD_FAVORITE_OK(list));

        }else return ResponseEntity.badRequest().body(Response.INVALID_DATATYPE(null));
    }

    @GetMapping("/{dataType}")
    public ResponseEntity<?> getFavoriteList(@RequestHeader("AccountId") String accountId, @PathVariable String dataType){

        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);

        if(account != null){
            if(dataType.equals("parking-lot")) {
                return ResponseEntity.ok().body(Response.GET_FAVORITE_LIST_OK(account.getParkingLotList()));
            }else if(dataType.equals("gas-station")){
                return ResponseEntity.ok().body(Response.GET_FAVORITE_LIST_OK(account.getGasStationList()));
            }else{
                return ResponseEntity.badRequest().body(Response.INVALID_DATATYPE(null));
            }
        }else{
            return ResponseEntity.badRequest().body(Response.INVALID_ACCOUNT_ID(null));
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteFavorite(@RequestHeader("AccountId") String accountId, @RequestBody FavoriteRequestDto dto){

        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);

        if(account!=null){
            if(dto.getType().equals("gas-station")){
                GasStation gasStation = gasStationRepository.findById(dto.getDataId()).orElse(null);
                if(gasStation == null) return ResponseEntity.badRequest().body(Response.INVALID_GAS_STATION_ID(null));
                account.getGasStationList().remove(gasStation);
                accountRepository.save(account);
                return ResponseEntity.ok().body(Response.DELETE_FAVORITE_OK(null));
            }else if(dto.getType().equals("parking-lot")) {
                ParkingLot parkingLot = parkingLotRepository.findById(dto.getDataId()).orElse(null);
                if(parkingLot == null) return ResponseEntity.badRequest().body(Response.INVALID_PARKINGLOT_ID(null));
                account.getParkingLotList().remove(parkingLot);
                accountRepository.save(account);
                return ResponseEntity.ok().body(Response.DELETE_FAVORITE_OK(null));
            }else return ResponseEntity.badRequest().body(Response.INVALID_DATATYPE(null));
        }else return ResponseEntity.badRequest().body(Response.INVALID_ACCOUNT_ID(null));
    }
}

