package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangeInfoFormValidator implements Validator {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(ChangeInfoForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangeInfoForm changeInfoForm = (ChangeInfoForm) o;
        if(accountRepository.existsByNickName(changeInfoForm.getNickname())){
            errors.rejectValue("nickname", "invalid.nickname", "이미 존재하는 닉네임 입니다.");
        }
    }
}
