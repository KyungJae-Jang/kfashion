package com.kfashion.kfashion.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickName(String nickname);

    Account findByEmail(String email);

    List<Account> findByEmailVerified(boolean b);
}
