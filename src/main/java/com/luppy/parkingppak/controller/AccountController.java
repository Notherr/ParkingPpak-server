package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.dto.*;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.JwtUtil;
import com.luppy.parkingppak.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        if (!validationUtil.validate_email(dto.getEmail())) return ResponseEntity.badRequest().body(Response.INVALID_EMAIL_ERROR());
        if (!validationUtil.validate_password(dto.getPassword())) return ResponseEntity.badRequest().body(Response.INVALID_PASSWORD_ERROR());

        AccountDto registeredAccount = accountService.joinAccount(dto);

        if(registeredAccount == null) return ResponseEntity.badRequest().body(Response.JOIN_ERROR());
        else return ResponseEntity.ok().body(Response.JOIN_OK(registeredAccount));
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
//
//        Optional<Account> account = accountRepository.findByEmail(dto.getEmail());
//
//        if (account.isEmpty()) return ResponseEntity.status(400).body(Response.NOT_JOIN_ERROR());
//        else {
//            if (!bCryptPasswordEncoder.matches(dto.getPassword(), account.get().getPassword())) {
//                return ResponseEntity.status(401).body(Response.PASSWORD_ERROR());
//            }
//
//            String jwtToken = "Bearer " + jwtUtil.createToken(account.get().getId(), account.get().getName());
//
//            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
//                    .email(account.get().getEmail())
//                    .name(account.get().getName())
//                    .jwt(jwtToken)
//                    .card(account.get().getCard())
//                    .oilType(account.get().getOilType())
//                    .naviType(account.get().getNaviType())
//                    .build();
//
//            return ResponseEntity.ok().body(Response.LOGIN_OK(loginResponseDto));
//        }
//    }

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
