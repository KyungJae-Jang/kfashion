package com.kfashion.kfashion.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired AccountRepository accountRepository;
    @Autowired JavaMailSender javaMailSender;
    @Autowired PasswordEncoder passwordEncoder;

    public Account processNewAccount(SignUpForm signUpForm) {
        Account account = createNewAccount(signUpForm);
        Account newAccount = accountRepository.save(account);
        newAccount.generateCheckToken();
        sendCheckEmailToken(newAccount);

        return newAccount;
    }

    private Account createNewAccount(SignUpForm signUpForm) {
        Account account = Account.builder()
                .nickName(signUpForm.getNickname())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        return account;
    }

    private void sendCheckEmailToken(Account newAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("K-Fashion 인증메일");
        msg.setText("/check-email-token?email=" + newAccount.getEmail()
                + "&token=" + newAccount.getEmailCheckToken());
        javaMailSender.send(msg);
    }

    public void login(Account account) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        account.getNickName(),
                        account.getPassword(),
                        authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void completeLogin(Account account) {
        account.setJoinedAt(LocalDateTime.now());
        account.setEmailVerified(true);
        login(account);
    }
}
