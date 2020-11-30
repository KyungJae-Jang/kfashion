package com.kfashion.kfashion.profile;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResetPwdFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(ResetPwdForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ResetPwdForm resetPwdForm = (ResetPwdForm) o;
        if(!resetPwdForm.getPassword().equals(resetPwdForm.getPasswordConfirmed())){
            errors.rejectValue("password", "invalid.password", "새 비밀번호가 일치하지 않습니다.");
        }
    }
}
