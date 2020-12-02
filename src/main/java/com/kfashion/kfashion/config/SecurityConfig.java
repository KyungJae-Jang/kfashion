package com.kfashion.kfashion.config;

import com.kfashion.kfashion.account.UserAccountService;
import lombok.RequiredArgsConstructor;
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

    private final UserAccountService userAccountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/", "/sign-up", "/checked-email", "/pwd-email-token",
                        "/find-password", "/free-boardList").permitAll()
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
                .userDetailsService(userAccountService);
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
