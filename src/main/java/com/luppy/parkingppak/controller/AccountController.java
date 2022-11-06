package com.luppy.parkingppak.controller;


import com.luppy.parkingppak.domain.dto.*;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.luppy.parkingppak.domain.dto.Response.response;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;
    private final ValidationUtil validationUtil;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        if (!validationUtil.validate_email(dto.getEmail())) return ResponseEntity.badRequest().body(response(400,null,"유효하지않은 이메일 형식입니다."));
        if (!validationUtil.validate_password(dto.getPassword())) return ResponseEntity.badRequest().body(response(400,null,"유효하지않은 패스워드 형식입니다."));

        AccountDto registeredAccount = accountService.joinAccount(dto);

        if(registeredAccount == null) return ResponseEntity.badRequest().body(response(400, null, "이미 존재하는 이메일입니다."));
        else return ResponseEntity.ok().body(response(200, registeredAccount, "회원가입이 정상적으로 되었습니다."));

    }

    @PutMapping("/accounts/card-type")
    public ResponseEntity<?> registerCard(@RequestHeader("AccountId") String accountId, @RequestBody CardDto cardDto) {

        CardDto registeredCard = accountService.registerCard(accountId, cardDto);
         return ResponseEntity.ok().body(response(200,registeredCard,"정상적으로 카드정보가 등록되었습니다."));
    }

    @PutMapping("/accounts/oil-type/{oilType}")
    public ResponseEntity<?> registerOilType(@RequestHeader("AccountId") String accountId, @PathVariable String oilType) {

        Optional<OilType> registeredOilType = accountService.registerOilType(accountId, oilType);
        return ResponseEntity.ok().body(response(200, registeredOilType, "정상적으로 유류정보가 등록되었습니다."));
    }

    @PutMapping("/accounts/navi-type/{naviType}")
    public ResponseEntity<?> registerNaviType(@RequestHeader("AccountId") String accountId, @PathVariable String naviType) {

        Optional<NaviType> registeredNaviType = accountService.registerNaviType(accountId ,naviType);
        return ResponseEntity.ok().body(response(200, registeredNaviType, "정상적으로 내비앱 정보가 등록되었습니다."));
    }
}
