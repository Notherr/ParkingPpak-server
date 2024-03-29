package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.Card;
import com.luppy.parkingppak.domain.CardRepository;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.CardDto;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
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
    public CardDto registerCard(String accountId, CardDto dto) {

        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        Card selectedCard = cardRepository.findByCardName(dto.getName());

        if(account == null) return null;
        if(selectedCard == null) {
            Card newCard = Card.builder()
                    .cardName(dto.getName())
                    .compName(dto.getCompName())
                    .content(dto.getContent())
                    .build();
            selectedCard = cardRepository.save(newCard);
        }
        account.setCard(selectedCard);
        accountRepository.save(account);
        return CardDto.builder()
                .id(selectedCard.getId())
                .name(selectedCard.getCardName())
                .compName(selectedCard.getCompName())
                .content(selectedCard.getContent())
                .build();
    }

    @Transactional
    public Optional<OilType> registerOilType(String accountId, String oilType) {

        Optional<Account> account = accountRepository.findById(Long.valueOf(accountId));

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    it.setOilType(OilType.valueOf(oilType));
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getOilType);
    }

    @Transactional
    public Optional<NaviType> registerNaviType(String accountId, String naviType) {

        Optional<Account> account = accountRepository.findById(Long.valueOf(accountId));

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    it.setNaviType(NaviType.valueOf(naviType));
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getNaviType);
    }
}
