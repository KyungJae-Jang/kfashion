package com.kfashion.kfashion.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AccountService {
    @Autowired AccountRepository accountRepository;
    @Autowired JavaMailSender javaMailSender;
    @Autowired PasswordEncoder passwordEncoder;

    public void processNewAccount(SignUpForm signUpForm) {
        Account account = createNewAccount(signUpForm);
        Account newAccount = accountRepository.save(account);
        newAccount.generateCheckToken();
        sendCheckEmailToken(newAccount);
    }

    private Account createNewAccount(SignUpForm signUpForm) {
        Account account = Account.builder()
                .nickName(signUpForm.getNickname())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .joinedAt(LocalDateTime.now())
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

}
