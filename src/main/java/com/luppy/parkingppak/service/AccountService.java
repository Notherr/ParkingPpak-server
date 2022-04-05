package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.CardRepository;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public AccountDto joinAccount(AccountDto dto) {

        // 메일이 존재하면 예외처리.
        Optional<Account> expected = accountRepository.findByEmail(dto.getEmail());

        if(expected.isPresent()) return null;
        else {
            String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
            Account account = Account.builder()
                    .email(dto.getEmail())
                    .name(dto.getName())
                    .password(encodedPassword)
                    .build();

            Account savedAccount = accountRepository.save(account);
            return AccountDto.builder()
                    .email(savedAccount.getEmail())
                    .name(savedAccount.getName())
                    .build();
        }
    }

    @Transactional
    public AccountDto login(LoginRequestDto dto) {
        Optional<Account> expected = accountRepository.findByEmail(dto.getEmail());

        if (expected.isEmpty()) return null;
        else {
            if (!bCryptPasswordEncoder.matches(dto.getPassword(), expected.get().getPassword())) return null;
            else {
                return AccountDto.builder()
                        .id(expected.get().getId())
                        .email(expected.get().getEmail())
                        .name(expected.get().getName())
                        .build();
            }
        }
    }

    @Transactional
    public Optional<Card> registerCard(String email, String card) {
        Optional<Account> account = accountRepository.findByEmail(email);
        Card selectedCard = cardRepository.findByName(card);

        if(account.isEmpty()) return Optional.empty();
        if(selectedCard == null) return Optional.empty();
        else return account
                .map(it ->{
                    it.setCard(selectedCard);
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getCard);
    }

    @Transactional
    public Optional<OilType> registerOilType(String email, String oilType) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    switch(oilType) {
                        case "LPG": it.setOilType(OilType.LPG);
                        case "휘발유": it.setOilType(OilType.GASOLINE);
                        case "경유": it.setOilType(OilType.VIA);
                        case "고급유": it.setOilType(OilType.PREMIUM);
                        case "전기": it.setOilType(OilType.ELECTRIC);
                    }

                    it.setOilType(OilType.LPG);
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getOilType);
    }

    @Transactional
    public Optional<NaviType> registerNavi(String email, String navi) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {

                    switch(navi){
                        case "카카오내비": it.setNaviType(NaviType.KAKAONAVI);
                        case "네이버지도": it.setNaviType(NaviType.NAVER);
                        case "구글지도": it.setNaviType(NaviType.GOOGLE);
                        case "카카오맵": it.setNaviType(NaviType.KAKAOMAP);
                        case "티맵": it.setNaviType(NaviType.TMAP);
                    }
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getNaviType);
    }
}
