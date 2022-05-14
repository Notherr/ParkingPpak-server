package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.CardRepository;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
import com.luppy.parkingppak.domain.enumclass.CardCompName;
import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

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
    public Optional<Card> registerCard(String jwt, String cardType) {
        String jwtToken = jwt.replace("Bearer ", "");

        Optional<Account> account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken)));
        Card selectedCard = cardRepository.findByCompName(CardCompName.valueOf(cardType));

        System.out.println(account);
        System.out.println(selectedCard);

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
    public Optional<OilType> registerOilType(String jwt, String oilType) {

        String jwtToken = jwt.replace("Bearer ", "");

        Optional<Account> account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken)));

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    switch(oilType) {
                        case "LPG":
                            it.setOilType(OilType.LPG);
                            break;
                        case "GASOLINE":
                            it.setOilType(OilType.GASOLINE);
                            break;
                        case "VIA":
                            it.setOilType(OilType.VIA);
                            break;
                        case "PREMIUM":
                            it.setOilType(OilType.PREMIUM);
                            break;
                        case "ELECTRIC":
                            it.setOilType(OilType.ELECTRIC);
                            break;
                    }
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getOilType);
    }

    @Transactional
    public Optional<NaviType> registerNaviType(String jwt, String naviType) {

        String jwtToken = jwt.replace("Bearer ", "");

        Optional<Account> account = accountRepository.findById(Long.valueOf(jwtUtil.getAccountId(jwtToken)));

        System.out.println(account);
        System.out.println(naviType);

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {

                    switch(naviType){
                        case "KAKAONAVI":
                            it.setNaviType(NaviType.KAKAONAVI);
                            break;
                        case "NAVER":
                            it.setNaviType(NaviType.NAVER);
                            break;
                        case "GOOGLE":
                            it.setNaviType(NaviType.GOOGLE);
                            break;
                        case "KAKAOMAP":
                            it.setNaviType(NaviType.KAKAOMAP);
                            break;
                        case "TMAP":
                            it.setNaviType(NaviType.TMAP);
                            break;
                    }
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getNaviType);
    }
}
