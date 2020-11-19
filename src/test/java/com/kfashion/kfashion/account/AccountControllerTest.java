package com.kfashion.kfashion.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void 회원가입_입력값_정상 () throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("nickname", "kyungjae")
                .param("email", "chong1175@naver.com")
                .param("password", "123456789")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void 회원가입_입력값_비정상 () throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("nickname", "12")
                .param("email", "11123")
                .param("password", "123456789")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }

}