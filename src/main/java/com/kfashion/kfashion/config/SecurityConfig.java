package com.kfashion.kfashion.config;

import com.kfashion.kfashion.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/", "/sign-up", "/checked-email", "/pwd-email-token",
                        "/find-password",
                        "/free-boardList", "/free-boardViewer").permitAll()
                .anyRequest().authenticated()
                .and()
        .formLogin()
                .loginPage("/login").permitAll()
                .and()
        .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
        .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 1000)
                .userDetailsService(accountService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**");
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
