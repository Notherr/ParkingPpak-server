package com.luppy.parkingppak.config.oauth;

import com.luppy.parkingppak.config.auth.AccountDetails;
import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()){
            //회원가입.
            Account newAccount = Account.builder()
                    .email(email)
                    .name(name)
                    .password(null)
                    .provider(provider)
                    .build();
            accountRepository.save(newAccount);

            return new AccountDetails(newAccount, oAuth2User.getAttributes());
        }else {
            Account account1 = Account.builder()
                    .email(account.get().getEmail())
                    .name(account.get().getName())
                    .password(account.get().getPassword())
                    .provider(account.get().getProvider())
                    .build();
            return new AccountDetails(account1, oAuth2User.getAttributes());
        }
    }
}
