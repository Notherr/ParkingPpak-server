package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.enumclass.CardCompName;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.luppy.parkingppak.domain.dto.Response.response;

@RestController
@RequestMapping("/api/option-info")
public class OptionInfoController {

    @GetMapping("/card-comp-name")
    public ResponseEntity<?> getCardCompNameList(){

        List<String> cardCompNameList = Stream.of(CardCompName.values()).map(Enum::name).collect(Collectors.toList());

        return ResponseEntity.ok().body(response(200, cardCompNameList, "정상적으로 카드 회사 이름 목록이 반환되었습니다." ));
    }

   @GetMapping("/oil-type")
    public ResponseEntity<?> getOilTypeList(){

        List<String> oilTypeList = Stream.of(OilType.values()).map(Enum::name).collect(Collectors.toList());

        return ResponseEntity.ok().body(response(200, oilTypeList, "정상적으로 유류 타입 목록이 반환되었습니다."));
    }

    @GetMapping("/navi-type")
    public ResponseEntity<?> getNaviTypeList(){

        List<String> naviTypeList = Stream.of(NaviType.values()).map(Enum::name).collect(Collectors.toList());

        return ResponseEntity.ok().body(response(200, naviTypeList, "정상적으로 내비 앱 목록이 반환되었습니다."));
    }
}
