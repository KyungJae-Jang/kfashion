package com.kfashion.kfashion.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;


    @Test
    public void 회원가입_입력값_정상 () throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("nickname", "kyungjae")
                .param("email", "chong1175@naver.com")
                .param("password", "123456789")
                .with(csrf()))
                .andDo(print())
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }

    @Test
    public void 인증메일_입력값_정상() throws Exception {
        Account account = Account.builder()
                .nickName("kyungjae")
                .email("chong1175@naver.com")
                .password("12345678")
                .build();
        Account newAccount = accountRepository.save(account);
        newAccount.generateCheckToken();

        mockMvc.perform(get("/check-email-token")
                    .param("email", newAccount.getEmail())
                    .param("token", newAccount.getEmailCheckToken())
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("email"))
                .andExpect(view().name("account/checked-email"));
    }

    @Test
    public void 인증메일_입력값_비정상() throws Exception {
        mockMvc.perform(get("/check-email-token")
                    .param("email", "chongsdfdsfer.com")
                    .param("token", "sdfsdfsdfsdfsdfsdf")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("account/checked-email"));
    }

}