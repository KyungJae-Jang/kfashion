package com.kfashion.kfashion.profile;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PasswordForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PasswordForm passwordForm = (PasswordForm) o;
        if(!passwordForm.getPassword().equals(passwordForm.getPasswordConfirmed())){
            errors.rejectValue("password", "invalid.password", "새 비밀번호 확인이 틀렸습니다.");
        }
    }
}
