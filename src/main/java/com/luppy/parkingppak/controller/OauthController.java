package com.luppy.parkingppak.controller;

import com.luppy.parkingppak.domain.dto.OauthKeyDto;
import com.luppy.parkingppak.domain.dto.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Value("${googleClientId}")
    private String googleClientId;

    @Value("${googleClientSecret}")
    private String googleClientSecret;

    @Value("${kakaoClientId}")
    private String kakaoClientId;
    @Value("${kakaoClientSecret}")
    private String kakaoClientSecret;




    @GetMapping("/{resourceServer}")
    public ResponseEntity<?> getOauthKey(@PathVariable String resourceServer){
        /*
        입력받은 파라미터에 맞는 키값을 반환한다.
         */

        if(resourceServer.equals("google")){
            OauthKeyDto oauthKeyDto = OauthKeyDto.builder()
                    .clientId(googleClientId)
                    .clientSecret(googleClientSecret)
                    .build();

            return ResponseEntity.ok().body(Response.GET_OAUTH_KEY_OK(oauthKeyDto));
        }else if(resourceServer.equals("kakao")){
            OauthKeyDto oauthKeyDto = OauthKeyDto.builder()
                    .clientId(kakaoClientId)
                    .clientSecret(kakaoClientSecret)
                    .build();
            return ResponseEntity.ok().body(Response.GET_OAUTH_KEY_OK(oauthKeyDto));
        }

        return ResponseEntity.badRequest().body(Response.INVALID_RESOURCE_SERVER_NAME(null));
    }
}
