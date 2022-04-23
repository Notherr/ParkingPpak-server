package com.luppy.parkingppak.config.oauth;

import com.luppy.parkingppak.config.auth.AccountDetails;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        String jwtToken = jwtUtil.createToken(accountDetails.getAccount().getId(), accountDetails.getUsername());

        response.addHeader("Authorization", "Bearer "+jwtToken);
    }
}
