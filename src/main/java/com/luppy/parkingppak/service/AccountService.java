package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.dto.AccountDto;
import com.luppy.parkingppak.domain.dto.SessionRequestDto;
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

    @Transactional
    public AccountDto createAccount(AccountDto dto) {

        Optional<Account> expected = accountRepository.findByEmail(dto.getEmail());

        if(expected.isEmpty()) return null;
        else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            Account account = Account.builder()
                    .email(dto.getEmail())
                    .name(dto.getName())
                    .password(encodedPassword)
                    .oilType(dto.getOilType())
                    .card(dto.getCard())
                    .build();

            Account savedAccount = accountRepository.save(account);
            return AccountDto.builder()
                    .email(savedAccount.getEmail())
                    .name(savedAccount.getName())
                    .oilType(savedAccount.getOilType())
                    .card(savedAccount.getCard())
                    .build();
        }
    }

    @Transactional
    public AccountDto authenticate(SessionRequestDto dto) {
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
}
