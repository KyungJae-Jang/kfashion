package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.AccountRepository;
import com.kfashion.kfashion.account.AccountService;
import com.kfashion.kfashion.account.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void beforeEach(){
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setEmail("chong1175@naver.com");
        signUpForm.setNickname("kyungjae");
        signUpForm.setPassword("12345678");
        accountService.processNewAccount(signUpForm);
    }

    @AfterEach
    public void afterEach(){
        accountRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void 패스워드메일_입력값_정상() throws Exception {
        Account account = accountRepository.findByEmail("chong1175@naver.com");

        mockMvc.perform(get("/pwd-email-token")
                        .param("email", account.getEmail())
                        .param("token", account.getEmailCheckToken())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("email"))
                .andExpect(view().name("account/found-password"))
                .andExpect(authenticated().withUsername("chong1175@naver.com"));
    }

    @Test
    @WithMockUser
    public void 패스워드메일_입력값_비정상() throws Exception {
        mockMvc.perform(get("/pwd-email-token")
                .param("email", "123123123123")
                .param("token", "123123123123132")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("account/found-password"));
    }

    @Test
    @WithMockUser
    public void 회원정보_변경_정상() throws Exception {

    }
}