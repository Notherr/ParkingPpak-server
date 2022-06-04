package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.dto.*;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.JwtUtil;
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

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공."),
            @ApiResponse(responseCode = "400", description = "회원가입 실패. - 이미 존재하는 이메일입니다.")
    })
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        AccountDto registeredAccount = accountService.joinAccount(dto);

        if(registeredAccount == null) return ResponseEntity.badRequest().body(Response.JOIN_ERROR());
        else return ResponseEntity.ok().body(Response.JOIN_OK(registeredAccount));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공."),
            @ApiResponse(responseCode = "4000", description = "로그인 실패. : 가입되지 않은 이메일입니다."),
            @ApiResponse(responseCode = "4001", description = "로그인 실패. : 틀린 패스워드 입니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        Optional<Account> account = accountRepository.findByEmail(dto.getEmail());

        if (account.isEmpty()) return ResponseEntity.status(400).body(Response.NOT_JOIN_ERROR());
        else {
            if (!bCryptPasswordEncoder.matches(dto.getPassword(), account.get().getPassword())) {
                return ResponseEntity.status(401).body(Response.PASSWORD_ERROR());
            }

            String jwtToken = "Bearer " + jwtUtil.createToken(account.get().getId(), account.get().getName());

            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .email(account.get().getEmail())
                    .name(account.get().getName())
                    .jwt(jwtToken)
                    .card(account.get().getCard())
                    .oilType(account.get().getOilType())
                    .naviType(account.get().getNaviType())
                    .build();

            return ResponseEntity.ok().body(Response.LOGIN_OK(loginResponseDto));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카드등록 성공.")
    })
    @PutMapping("/accounts/card-type")
    public ResponseEntity<?> registerCard(@RequestHeader("Authorization") String jwt, @RequestBody CardDto cardDto) {

        CardDto registeredCard = accountService.registerCard(jwt, cardDto);

        if(registeredCard == null) return ResponseEntity.badRequest().body(Response.REGISTER_CARD_ERROR(cardDto));
        return ResponseEntity.ok().body(Response.REGISTER_CARD_OK(registeredCard));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유류정보등록 성공.")
    })
    @PutMapping("/accounts/oil-type/{oilType}")
    public ResponseEntity<?> registerOilType(@RequestHeader("Authorization") String jwt, @PathVariable String oilType) {

        Optional<OilType> registeredOilType = accountService.registerOilType(jwt, oilType);

        if(registeredOilType.isEmpty()) return ResponseEntity.badRequest().body(Response.REGISTER_OIL_TYPE_ERROR(oilType));
        return ResponseEntity.ok().body(Response.REGISTER_OIL_TYPE_OK(registeredOilType));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "네비게이션앱정보등록 성공.")
    })
    @PutMapping("/accounts/navi-type/{naviType}")
    public ResponseEntity<?> registerNaviType(@RequestHeader("Authorization") String jwt, @PathVariable String naviType) {

        Optional<NaviType> registeredNaviType = accountService.registerNaviType(jwt ,naviType);

        if(registeredNaviType.isEmpty()) return ResponseEntity.badRequest().body(Response.REGISTER_NAVI_TYPE_ERROR(naviType));
        return ResponseEntity.ok().body(Response.REGISTER_NAVI_TYPE_OK(registeredNaviType));
    }
}
