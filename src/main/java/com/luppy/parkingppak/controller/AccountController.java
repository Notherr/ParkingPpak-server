package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.SessionRequestDto;
import com.luppy.parkingppak.domain.dto.SessionResponseDto;
import com.luppy.parkingppak.service.AccountService;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AccountDto dto) {
        return ResponseEntity.ok().body(accountService.createAccount(dto));
    }

    @PostMapping("/session")
    public ResponseEntity<?> authenticate(@RequestBody SessionRequestDto dto) {

        AccountDto accountDto = accountService.authenticate(dto);

        String accessToken = jwtUtil.createToken(accountDto.getId(), accountDto.getName());

        return ResponseEntity.ok().body(
                SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
}
