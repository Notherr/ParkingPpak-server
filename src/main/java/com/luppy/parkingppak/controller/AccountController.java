package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
import com.luppy.parkingppak.domain.dto.LoginResponseDto;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.JwtUtil;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        Optional<Account> account = accountRepository.findByEmail(dto.getEmail());

        if (account.isEmpty()) return ResponseEntity.badRequest().body("가입되지 않은 이메일입니다.");
        else {
            if (!bCryptPasswordEncoder.matches(dto.getPassword(), account.get().getPassword())) {
                return ResponseEntity.badRequest().body("틀린 패스워드 입니다.");
            }

            String jwtToken = "Bearer " + jwtUtil.createToken(account.get().getId(), account.get().getName());

            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .email(account.get().getEmail())
                    .name(account.get().getName())
                    .jwt(jwtToken)
                    .build();

            return ResponseEntity.ok().body(loginResponseDto);
        }
    }

    @PutMapping("/accounts/cards/{email}/{card}")
    public ResponseEntity<?> registerCard(@PathVariable String email, @PathVariable String card) {

        Optional<Card> registeredCard = accountService.registerCard(email, card);

        return ResponseEntity.ok().body(registeredCard);
    }

    @PutMapping("/api/accounts/oil-type/{email}/{oilType}")
    public ResponseEntity<?> registerOilType(@PathVariable String email, @PathVariable String oilType) {

        Optional<OilType> registeredOilType = accountService.registerOilType(email,oilType);

        return ResponseEntity.ok().body(registeredOilType);
    }

    @PutMapping("/accounts/navi/{email}/{navi}")
    public ResponseEntity<?> registerNavi(@PathVariable String email, @PathVariable String navi) {

        Optional<NaviType> registeredNavi = accountService.registerNavi(email,navi);

        return ResponseEntity.ok().body(registeredNavi);
    }

}
