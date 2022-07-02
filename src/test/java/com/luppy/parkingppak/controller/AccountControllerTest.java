
package com.luppy.parkingppak.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luppy.parkingppak.domain.Account;
import com.luppy.parkingppak.domain.AccountRepository;
import com.luppy.parkingppak.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @Transactional
    public void 회원가입이_정상적으로_되었습니다 () throws Exception {

        Map<String, String> joinContent = new HashMap<>();
        joinContent.put("email", "test@gmail.com");
        joinContent.put("password", "1234");
        joinContent.put("name", "seongkyu");

        mockMvc.perform(post("/api/join")
                .content(objectMapper.writeValueAsString(joinContent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 이미_존재하는_이메일입니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("seongkyu")
                .build();
        accountRepository.save(account);

        Map<String, String> joinContent = new HashMap<>();
        joinContent.put("email", "test@gmail.com");
        joinContent.put("password", "1234");
        joinContent.put("name", "seongkyu");

        mockMvc.perform(post("/api/join")
                        .content(objectMapper.writeValueAsString(joinContent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(400));

    }

    @Test
    @Transactional
    public void 로그인이_정상적으로_되었습니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password(bCryptPasswordEncoder.encode("1234"))
                .name("seongkyu")
                .build();
        accountRepository.save(account);

        Map<String, String> loginContent = new HashMap<>();
        loginContent.put("email", "test@gmail.com");
        loginContent.put("password", "1234");

        mockMvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(loginContent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @Transactional
    public void 가입되지_않은_이메일입니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password(bCryptPasswordEncoder.encode("1234"))
                .name("seongkyu")
                .build();
        accountRepository.save(account);

        Map<String, String> loginContent = new HashMap<>();
        loginContent.put("email", "test@naver.com");
        loginContent.put("password", "1234");

        mockMvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(loginContent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(400));

    }

    @Test
    @Transactional
    public void 틀린_패스워드_입니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password(bCryptPasswordEncoder.encode("1234"))
                .name("seongkyu")
                .build();
        accountRepository.save(account);

        Map<String, String> loginContent = new HashMap<>();
        loginContent.put("email", "test@gmail.com");
        loginContent.put("password", "1111");

        mockMvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(loginContent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(401));

    }

    @Test
    @Transactional
    public void 카드정보가_정상적으로_등록되었습니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("seongkyu")
                .build();
        Account savedAccount = accountRepository.save(account);

        String jwt = "Bearer " + jwtUtil.createToken(savedAccount.getId(), savedAccount.getName());

        Map<String, String> content = new HashMap<>();
        content.put("name", "SINHAN ZERO CARD");
        content.put("compName", "SINHAN");
        content.put("content", "3%");

        mockMvc.perform(put("/api/accounts/card-type")
                        .header("Authorization", jwt)
                        .content(objectMapper.writeValueAsString(content))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 정상적으로_유류정보가_등록되었습니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("seongkyu")
                .build();
        Account savedAccount = accountRepository.save(account);

        String jwt = "Bearer " + jwtUtil.createToken(savedAccount.getId(), savedAccount.getName());

        mockMvc.perform(put("/api/accounts/oil-type/LPG")
                        .header("Authorization", jwt)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 정상적으로_내비앱_정보가_등록되었습니다 () throws Exception {

        Account account = Account.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("seongkyu")
                .build();
        Account savedAccount = accountRepository.save(account);

        String jwt = "Bearer " + jwtUtil.createToken(savedAccount.getId(), savedAccount.getName());

        mockMvc.perform(put("/api/accounts/navi-type/KAKAOMAP")
                        .header("Authorization", jwt)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}

