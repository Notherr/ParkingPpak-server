package com.luppy.parkingppak.config;

import com.luppy.parkingppak.config.auth.CustomOAuth2UserService;
import com.luppy.parkingppak.config.jwt.JwtAuthorizationFilter;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable();
        http
                //.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUtil, accountRepository))
                .authorizeRequests()
                //.antMatchers("/api/accounts/**").access("hasRole('ACCOUNT')")
                .antMatchers("/api/accounts/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
                //.and()
                //.formLogin()
                //.loginPage("/login");
    }
}
