package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findAccountByAccountId(Integer accountId);

    public Optional<Account> findAccountByUsername(String username);
}
