package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
import com.luppy.parkingppak.domain.dto.LoginResponseDto;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AccountDto dto) {

        AccountDto createdAccount = accountService.joinAccount(dto);

        if(createdAccount == null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok().body(createdAccount);
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
