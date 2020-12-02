package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void updateInfo(Account account, ChangeInfoForm changeInfoForm) {
        account.updateInfo(changeInfoForm.getNewNickName(),
                changeInfoForm.isCommentPostedByEmail(), changeInfoForm.isCommentPostedByWeb());
        accountRepository.save(account);
    }

    public void updatePassword(Account account, ResetPwdForm resetPwdForm){
        account.updatePassword(passwordEncoder.encode(resetPwdForm.getPassword()));
        accountRepository.save(account);
    }

    public void deleteAccount(DeleteAccountForm deleteAccountForm) {
        Account account = accountRepository.findByEmail(deleteAccountForm.getEmail());
        accountRepository.delete(account);
    }
}
