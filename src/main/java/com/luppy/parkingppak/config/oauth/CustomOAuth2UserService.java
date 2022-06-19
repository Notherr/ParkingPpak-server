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

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String email = "";
        String name = "";

        if (provider.equals("google")) {
            name = oAuth2User.getAttribute("name");
            email = oAuth2User.getAttribute("email");
        }else if (provider.equals("kakao")){

            Map<String, Object> attributesAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");;
            Map<String, Object> attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
            name = attributesProfile.get("nickname").toString();
            email = attributesAccount.get("email").toString();
        }

        Account account = accountRepository.findByEmail(email).orElse(null);

        if(account == null){
            //join.
            Account newAccount = Account.builder()
                    .email(email)
                    .name(name)
                    .password(null)
                    .provider(provider)
                    .build();
            accountRepository.save(newAccount);

            return new AccountDetails(newAccount, oAuth2User.getAttributes());
        }else {
            //update.
            account.setEmail(email);
            account.setName(name);
            account.setProvider(provider);
            accountRepository.save(account);

            return new AccountDetails(account, oAuth2User.getAttributes());
        }
    }
}
