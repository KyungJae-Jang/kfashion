package com.kfashion.kfashion.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AccountService implements UserDetailsService {
    @Autowired AccountRepository accountRepository;
    @Autowired JavaMailSender javaMailSender;
    @Autowired PasswordEncoder passwordEncoder;

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
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("K-Fashion 인증 메일");
        msg.setText("/check-email-token?email=" + newAccount.getEmail()
                + "&token=" + newAccount.getEmailCheckToken());
        javaMailSender.send(msg);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserAccount(account);
    }

    public void sendPwdEmailToken(FindPwdForm findPwdForm) {
        Account account = accountRepository.findByEmail(findPwdForm.getEmail());

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("K-Fashion 로그인 메일");
        msg.setText("/pwd-email-token?email=" + account.getEmail()
                + "&token=" + account.getEmailCheckToken());
        javaMailSender.send(msg);
    }
}
