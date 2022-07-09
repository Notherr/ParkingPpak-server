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

    @Value("${googleClientId}")
    private String googleClientId;

    @Value("${googleClientSecret}")
    private String googleClientSecret;

    @Value("${kakaoClientId}")
    private String kakaoClientId;
    @Value("${kakaoClientSecret}")
    private String kakaoClientSecret;

    @Value("${googleClientSecretForSearch}")
    private String googleClientSecretForSearch;

    @GetMapping("/{resourceServer}")
    public ResponseEntity<?> getOauthKey(@PathVariable String resourceServer){
        /*
        입력받은 파라미터에 맞는 키값을 반환한다.
         */

        switch (resourceServer) {
            case "google": {
                OauthKeyDto oauthKeyDto = OauthKeyDto.builder()
                        .clientId(googleClientId)
                        .clientSecret(googleClientSecret)
                        .build();

                return ResponseEntity.ok().body(Response.GET_OAUTH_KEY_OK(oauthKeyDto));
            }
            case "kakao": {
                OauthKeyDto oauthKeyDto = OauthKeyDto.builder()
                        .clientId(kakaoClientId)
                        .clientSecret(kakaoClientSecret)
                        .build();
                return ResponseEntity.ok().body(Response.GET_OAUTH_KEY_OK(oauthKeyDto));
            }
            case "google-search": {
                OauthKeyDto oauthKeyDto = OauthKeyDto.builder()
                        .clientId(null)
                        .clientSecret(googleClientSecretForSearch)
                        .build();
                return ResponseEntity.ok().body(Response.GET_OAUTH_KEY_OK(oauthKeyDto));
            }
        }

        return ResponseEntity.badRequest().body(Response.INVALID_RESOURCE_SERVER_NAME(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> oauthLogin(@RequestBody OauthDto oauthDto){

        LoginResponseDto loginResponseDto = oauthService.login(oauthDto);
        return ResponseEntity.ok().body(Response.OAUTH_LOGIN_OK(loginResponseDto));
    }
}
