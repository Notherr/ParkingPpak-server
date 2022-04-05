package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import com.luppy.parkingppak.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        AccountDto registeredAccount = accountService.joinAccount(dto);

        if(registeredAccount == null) return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
        else return ResponseEntity.ok().body(registeredAccount);
    }

    @PutMapping("/accounts/cards/{email}/{card}")
    public ResponseEntity<?> registerCard(@PathVariable String email, @PathVariable String card) {

        Optional<Card> registeredCard = accountService.registerCard(email, card);

        return ResponseEntity.ok().body(registeredCard);
    }

    @PutMapping("/accounts/oil-type/{email}/{oilType}")
    public ResponseEntity<?> registerOilType(@PathVariable String email, @PathVariable String oilType) {

        Optional<OilType> registeredOilType = accountService.registerOilType(email,oilType);

        return ResponseEntity.ok().body(registeredOilType);
    }

    @PutMapping("/accounts/navi/{email}/{navi}")
    public ResponseEntity<?> registerNavi(@PathVariable String email, @PathVariable String navi) {

        Optional<NaviType> registeredNavi = accountService.registerNavi(email,navi);

        return ResponseEntity.ok().body(registeredNavi);
    }

    /*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        AccountDto accountDto = accountService.login(dto);

        if (accountDto == null) return ResponseEntity.notFound().build();
        else {
            String accessToken = jwtUtil.createToken(accountDto.getId(), accountDto.getName());

            return ResponseEntity.ok().body(
                    LoginResponseDto.builder()
                            .accessToken(accessToken)
                            .build());
        }
    }
     */
}
