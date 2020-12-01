package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void updateInfo(Account account, ChangeInfoForm changeInfoForm) {
        account.setNickName(changeInfoForm.getNewNickName());
        account.setCommentPostedByEmail(changeInfoForm.isCommentPostedByEmail());
        account.setCommentPostedByWeb(changeInfoForm.isCommentPostedByWeb());
        accountRepository.save(account);
    }

    public void updatePassword(Account account, ResetPwdForm resetPwdForm){
        account.setPassword(passwordEncoder.encode(resetPwdForm.getPassword()));
        accountRepository.save(account);
    }

    public void deleteAccount(DeleteAccountForm deleteAccountForm) {
        Account account = accountRepository.findByEmail(deleteAccountForm.getEmail());
        accountRepository.delete(account);
    }
}
