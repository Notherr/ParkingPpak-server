package com.luppy.parkingppak.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegrationTest")
@Disabled
class OptionInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void 정상적으로_카드_회사_이름_목록이_반환되었습니다 () throws Exception {

        mockMvc.perform(get("/api/option-info/card-comp-name")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 정상적으로_유류_타입_목록이_반환되었습니다 () throws Exception {

        mockMvc.perform(get("/api/option-info/oil-type")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 정상적으로_내비_앱_목록이_반환되었습니다 () throws Exception {

        mockMvc.perform(get("/api/option-info/navi-type")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
