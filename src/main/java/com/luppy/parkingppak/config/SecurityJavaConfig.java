package com.luppy.parkingppak.config;

import com.luppy.parkingppak.config.filter.CustomFilter;
import com.luppy.parkingppak.config.filter.JwtAuthenticationFilter;
import com.luppy.parkingppak.config.filter.JwtAuthorizationFilter;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil);
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable();
        http
                .addFilter(getJwtAuthenticationFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUtil, accountRepository))
                .addFilterAfter(new CustomFilter(jwtUtil), JwtAuthorizationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/accounts/**").authenticated()
                .anyRequest().permitAll();
    }
}
