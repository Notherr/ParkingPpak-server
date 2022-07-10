package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.LoginResponseDto;
import com.luppy.parkingppak.domain.dto.OauthDto;
import com.luppy.parkingppak.domain.dto.OauthKeyDto;
import com.luppy.parkingppak.domain.dto.Response;
import com.luppy.parkingppak.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/{resourceServer}")
    public ResponseEntity<?> getOauthKey(@PathVariable String resourceServer){
        /*
        입력받은 파라미터에 맞는 키값을 반환한다.
         */
        OauthKeyDto oauthKeyDto = oauthService.getOauthKey(resourceServer);
        if(oauthKeyDto == null) return ResponseEntity.badRequest().body(Response.INVALID_RESOURCE_SERVER_NAME(null));
        return ResponseEntity.ok().body(Response.GET_OAUTH_KEY_OK(oauthKeyDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> oauthLogin(@RequestBody OauthDto oauthDto){
        /*
        클라이언트로부터 받은 oauth 로그인 결과값으로 회원가입 혹은 유저 정보 업데이트를 진행한다.
        jwt키를 반환한다.
         */

        LoginResponseDto loginResponseDto = oauthService.login(oauthDto);
        return ResponseEntity.ok().body(Response.OAUTH_LOGIN_OK(loginResponseDto));
    }
}
