package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DeleteAccountFormValidator implements Validator {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(DeleteAccountForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DeleteAccountForm deleteAccountForm = (DeleteAccountForm) o;
        Account account = accountRepository.findByEmail(deleteAccountForm.getEmail());
        if(!passwordEncoder.matches(deleteAccountForm.getPassword(), account.getPassword())){
            errors.rejectValue("password", "invalid.password", "비밀번호가 일치하지 않습니다.");
        }
    }
}
