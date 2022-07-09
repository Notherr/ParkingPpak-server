package com.luppy.parkingppak.service;

import com.luppy.parkingppak.config.auth.AccountDetails;
import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.LoginResponseDto;
import com.luppy.parkingppak.domain.dto.OauthDto;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;


    public LoginResponseDto login(OauthDto oauthDto) {

        /*
        - 기존 유저 정보가 없으면 회원가입.
        - 기존 유저 정보가 있으면 업데이트.
         */

        Account account = accountRepository.findByEmail(oauthDto.getEmail()).orElse(null);

        if (account == null) {
            //join.
            Account newAccount = Account.builder()
                    .email(oauthDto.getEmail())
                    .name(oauthDto.getName())
                    .password(null)
                    .provider(oauthDto.getProvider())
                    .build();
            accountRepository.save(newAccount);

            String jwtToken = "Bearer " + jwtUtil.createToken(newAccount.getId(), newAccount.getName());

            return LoginResponseDto.builder()
                    .email(newAccount.getEmail())
                    .name(newAccount.getName())
                    .jwt(jwtToken)
                    .build();
        } else {
            //update.
            account.setEmail(oauthDto.getEmail());
            account.setName(oauthDto.getName());
            account.setProvider(oauthDto.getProvider());
            accountRepository.save(account);

            String jwtToken = "Bearer " + jwtUtil.createToken(account.getId(), account.getName());

            return LoginResponseDto.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .jwt(jwtToken)
                    .card(account.getCard())
                    .oilType(account.getOilType())
                    .naviType(account.getNaviType())
                    .build();
        }
    }
}
