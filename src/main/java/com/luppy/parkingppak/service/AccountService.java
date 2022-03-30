package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.dto.AccountDto;
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
    public Optional<String> registerCard(String email, String card) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    it.setCard(card);
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getCard);
    }

    public Optional<String> registerOilType(String email, String oilType) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    it.setOilType(oilType);
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getOilType);
    }

    public Optional<String> registerNavi(String email, String navi) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()) return Optional.empty();
        else return account
                .map(it -> {
                    it.setNavi(navi);
                    return it;
                })
                .map(accountRepository::save)
                .map(Account::getNavi);
    }




    /*
    @Transactional
    public AccountDto login(LoginRequestDto dto) {
        Optional<Account> expected = accountRepository.findByEmail(dto.getEmail());

        if (expected.isEmpty()) return null;
        else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(dto.getPassword(), expected.get().getPassword())) return null;
            else {
                return AccountDto.builder()
                        .id(expected.get().getId())
                        .email(expected.get().getEmail())
                        .name(expected.get().getName())
                        .oilType(expected.get().getOilType())
                        .card(expected.get().getCard())
                        .build();
            }
        }
    }
     */
}
