package com.kfashion.kfashion.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("local")
public class ConsoleEmailService implements EmailService{
    @Override
    public void send(EmailMessage emailMessage) {
        log.info("send email : " + emailMessage.getMessage());
    }
}
