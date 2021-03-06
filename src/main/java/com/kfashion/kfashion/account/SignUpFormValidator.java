package com.kfashion.kfashion.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) o;
        if(accountRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email", "invalid.email", "이미 존재하는 이메일 입니다.");
        }

        if(accountRepository.existsByNickName(signUpForm.getNickname())){
            errors.rejectValue("nickname", "invalid.nickname", "이미 존재하는 닉네임 입니다.");
        }
    }
}
