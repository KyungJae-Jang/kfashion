package com.kfashion.kfashion.profile;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResetPwFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(ResetPwForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ResetPwForm resetPwForm = (ResetPwForm) o;
        if(!resetPwForm.getPassword().equals(resetPwForm.getPasswordConfirmed())){
            errors.rejectValue("password", "invalid.password", "새 비밀번호가 일치하지 않습니다.");
        }
    }
}
