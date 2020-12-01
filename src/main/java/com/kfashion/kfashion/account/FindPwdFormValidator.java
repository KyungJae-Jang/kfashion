package com.kfashion.kfashion.account;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FindPwdFormValidator implements Validator {
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(FindPwdForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        FindPwdForm findPwdForm = (FindPwdForm) o;
        if(!accountRepository.existsByEmail(findPwdForm.getEmail())){
            errors.rejectValue("email", "invalid.email","존재하지 않는 이메일입니다.");
        }
    }
}
