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

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;
    private final ValidationUtil validationUtil;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        if (!validationUtil.validate_email(dto.getEmail())) return ResponseEntity.badRequest().body(Response.INVALID_EMAIL_ERROR());
        if (!validationUtil.validate_password(dto.getPassword())) return ResponseEntity.badRequest().body(Response.INVALID_PASSWORD_ERROR());

        AccountDto registeredAccount = accountService.joinAccount(dto);

        if(registeredAccount == null) return ResponseEntity.badRequest().body(Response.JOIN_ERROR());
        else return ResponseEntity.ok().body(Response.JOIN_OK(registeredAccount));
    }

    @PutMapping("/accounts/card-type")
    public ResponseEntity<?> registerCard(@RequestHeader("Authorization") String jwt, @RequestBody CardDto cardDto) {

        CardDto registeredCard = accountService.registerCard(jwt, cardDto);
         return ResponseEntity.ok().body(Response.REGISTER_CARD_OK(registeredCard));
    }

    @PutMapping("/accounts/oil-type/{oilType}")
    public ResponseEntity<?> registerOilType(@RequestHeader("Authorization") String jwt, @PathVariable String oilType) {

        Optional<OilType> registeredOilType = accountService.registerOilType(jwt, oilType);
        return ResponseEntity.ok().body(Response.REGISTER_OIL_TYPE_OK(registeredOilType));
    }

    @PutMapping("/accounts/navi-type/{naviType}")
    public ResponseEntity<?> registerNaviType(@RequestHeader("Authorization") String jwt, @PathVariable String naviType) {

        Optional<NaviType> registeredNaviType = accountService.registerNaviType(jwt ,naviType);
        return ResponseEntity.ok().body(Response.REGISTER_NAVI_TYPE_OK(registeredNaviType));
    }
}
