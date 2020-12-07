package com.kfashion.kfashion.account;

import com.kfashion.kfashion.mail.EmailMessage;
import com.kfashion.kfashion.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    public Account processNewAccount(SignUpForm signUpForm) {
        Account account = createNewAccount(signUpForm);
        Account newAccount = accountRepository.save(account);
        sendCheckEmailToken(newAccount);

        return newAccount;
    }

    private Account createNewAccount(SignUpForm signUpForm) {
        Account account = Account.builder()
                .nickName(signUpForm.getNickname())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .joinedAt(LocalDateTime.now())
                .build();
        account.generateCheckToken();

        return account;
    }

    public void sendCheckEmailToken(Account newAccount) {
        Context context = new Context();
        context.setVariable("nickname", newAccount.getNickName());
        context.setVariable("link", "/check-email-token?email=" + newAccount.getEmail()
                + "&token=" + newAccount.getEmailCheckToken());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "K-Fashion 서비스를 이용하려면 링크를 클릭하세요.");
        context.setVariable("host", "http://localhost:8080");

        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("K-Fashion 인증 메일")
                .message(message)
                .build();
        emailService.send(emailMessage);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        new UserAccount(account),
                        account.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void completeLogin(Account account) {
        account.setEmailVerified(true);
        login(account);
    }

    public void sendPwdEmailToken(FindPwdForm findPwdForm) {
        Account account = accountRepository.findByEmail(findPwdForm.getEmail());

        Context context = new Context();
        context.setVariable("nickname", account.getNickName());
        context.setVariable("link", "/pwd-email-token?email=" + account.getEmail()
                + "&token=" + account.getEmailCheckToken());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "K-Fashion에 로그인하려면 링크를 클릭하세요.");
        context.setVariable("host", "http://localhost:8080");

        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(account.getEmail())
                .subject("K-Fashion 로그인 메일")
                .message(message)
                .build();
        emailService.send(emailMessage);
    }
}
