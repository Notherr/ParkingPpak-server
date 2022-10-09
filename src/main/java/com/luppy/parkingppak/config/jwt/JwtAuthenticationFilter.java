package com.luppy.parkingppak.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luppy.parkingppak.config.auth.AccountDetails;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.domain.dto.LoginRequestDto;
import com.luppy.parkingppak.domain.dto.LoginResponseDto;
import com.luppy.parkingppak.domain.dto.Response;
import com.luppy.parkingppak.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;



    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper om = new ObjectMapper();
        try {
            LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(),
                    loginRequestDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        AccountDetails accountDetails = (AccountDetails) authResult.getPrincipal();

        String jwtToken = jwtUtil.createToken(accountDetails.getAccount().getId(), accountDetails.getUsername());

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .email(accountDetails.getAccount().getEmail())
                    .name(accountDetails.getAccount().getName())
                    .jwt("Bearer "+jwtToken)
                    .card(accountDetails.getAccount().getCard())
                    .oilType(accountDetails.getAccount().getOilType())
                    .naviType(accountDetails.getAccount().getNaviType())
                    .build();

        PrintWriter writer = response.getWriter();
        ObjectMapper om= new ObjectMapper();
        String jsonString = om.writeValueAsString(Response.LOGIN_OK(loginResponseDto));
        response.setHeader("Content-Type", "application/json");
        response.setContentType("application/json");
        writer.print(jsonString);
        writer.flush();

//        response.addHeader("Authorization", "Bearer "+jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        PrintWriter writer = response.getWriter();
        ObjectMapper om= new ObjectMapper();
        String jsonString = "";
        if(failed instanceof AuthenticationServiceException) {
            jsonString = om.writeValueAsString(Response.NOT_JOIN_ERROR());
        }else if (failed instanceof BadCredentialsException){
            jsonString = om.writeValueAsString(Response.PASSWORD_ERROR());
        }else{
            jsonString = om.writeValueAsString(Response.SYSTEM_ERROR());
        }
        response.setHeader("Content-Type", "application/json");
        response.setContentType("application/json");
        writer.print(jsonString);
        writer.flush();
    }
}