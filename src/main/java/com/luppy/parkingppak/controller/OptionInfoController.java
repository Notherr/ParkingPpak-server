package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.Response;
import com.luppy.parkingppak.domain.enumclass.CardCompName;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.luppy.parkingppak.domain.dto.Response.response;

@RestController
@RequestMapping("/api/option-info")
public class OptionInfoController {

    @GetMapping("/card-comp-name")
    public ResponseEntity<?> getCardCompNameList(){

        List<CardCompName> cardCompNameList = new ArrayList<>();
        cardCompNameList.add(CardCompName.HYUNDAI);
        cardCompNameList.add(CardCompName.KB);
        cardCompNameList.add(CardCompName.SAMSUNG);
        cardCompNameList.add(CardCompName.SINHAN);

        return ResponseEntity.ok().body(response(200, cardCompNameList, "정상적으로 카드 회사 이름 목록이 반환되었습니다." ));
    }

    @GetMapping("/oil-type")
    public ResponseEntity<?> getOilTypeList(){
        List<OilType> oilTypeList = new ArrayList<>();
        oilTypeList.add(OilType.GASOLINE);
        oilTypeList.add(OilType.VIA);
        oilTypeList.add(OilType.ELECTRIC);
        oilTypeList.add(OilType.PREMIUM);
        oilTypeList.add(OilType.LPG);

        return ResponseEntity.ok().body(response(200, oilTypeList, "정상적으로 유류 타입 목록이 반환되었습니다."));
    }

    @GetMapping("/navi-type")
    public ResponseEntity<?> getNaviTypeList(){
        List<NaviType> naviTypeList = new ArrayList<>();
        naviTypeList.add(NaviType.GOOGLE);
        naviTypeList.add(NaviType.KAKAOMAP);
        naviTypeList.add(NaviType.KAKAONAVI);
        naviTypeList.add(NaviType.NAVER);
        naviTypeList.add(NaviType.TMAP);

        return ResponseEntity.ok().body(response(200, naviTypeList, "정상적으로 내비 앱 목록이 반환되었습니다."));
    }
}
