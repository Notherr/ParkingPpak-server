package com.luppy.parkingppak.config.jwt;

import com.luppy.parkingppak.config.auth.AccountDetails;
import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.webjars.NotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AccountRepository accountRepository) {

        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        String jwt = request.getHeader("Authorization").replace("Bearer ", "");

        //if(jwtUtil.validateToken(jwt)){
            String accountId = jwtUtil.getAccountId(jwt);

            if (accountId != null) {
                Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);

                if(account == null) {
                    chain.doFilter(request, response);
                    return;
                }
                else {
                    AccountDetails accountDetails = new AccountDetails(account);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(accountDetails, null, accountDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        //}
        chain.doFilter(request,response);
    }
}
