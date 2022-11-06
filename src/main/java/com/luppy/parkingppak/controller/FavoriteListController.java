package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.FavoriteRequestDto;
import com.luppy.parkingppak.domain.dto.GasStationDto;
import com.luppy.parkingppak.domain.dto.ParkingLotDto;
import com.luppy.parkingppak.service.FavoriteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.luppy.parkingppak.domain.dto.Response.response;

@RestController
@RequestMapping("/api/accounts/favorite-list")
@RequiredArgsConstructor
public class FavoriteListController {

    private final FavoriteListService favoriteListService;

    @PostMapping()
    public ResponseEntity<?> favoriteAdd(@RequestHeader("AccountId") String accountId, @RequestBody FavoriteRequestDto dto){

        if(dto.getType().equals("parking-lot")){

            List<ParkingLotDto> list = favoriteListService.addParkingLotFavorite(accountId, dto);
            if (list == null) return ResponseEntity.badRequest().body(response(400, null, "잘못된 요청입니다."));
            return ResponseEntity.ok().body(response(200, list, "정상적으로 찜목록에 추가되었습니다."));

        }else if(dto.getType().equals("gas-station")){

            List<GasStationDto> list = favoriteListService.addGasStationFavorite(accountId, dto);
            if (list == null) return ResponseEntity.badRequest().body(response(400, null, "잘못된 요청입니다."));
            return ResponseEntity.ok().body(response(200, list, "정상적으로 찜목록에 추가되었습니다."));

        }else return ResponseEntity.badRequest().body(response(400, null, "잘못된 데이터 타입입니다.(type : parking-lot or gas-station)"));
    }

    @GetMapping("/{dataType}")
    public ResponseEntity<?> FavoriteList(@RequestHeader("AccountId") String accountId, @PathVariable String dataType){

        if(dataType.equals("parking-lot")) {
            List<ParkingLotDto> list = favoriteListService.findParkingLotFavoriteList(accountId);
            return ResponseEntity.ok().body(response(200, list, "정상적으로 리스트가 반환되었습니다."));
        }else if(dataType.equals("gas-station")){
            List<GasStationDto> list = favoriteListService.findGasStationFavoriteList(accountId);
            return ResponseEntity.ok().body(response(200, list, "정상적으로 리스트가 반환되었습니다."));
        }else{
            return ResponseEntity.badRequest().body(response(400, null, "잘못된 데이터 타입입니다.(type : parking-lot or gas-station)"));
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> FavoriteRemove(@RequestHeader("AccountId") String accountId, @RequestBody FavoriteRequestDto dto){

        String status = favoriteListService.removeFavorite(accountId, dto);
        if(status == null) return ResponseEntity.badRequest().body(response(400, null, "잘못된 요청입니다."));
        else return ResponseEntity.ok().body(response(200, null, "정상적으로 삭제되었습니다."));
    }
}

