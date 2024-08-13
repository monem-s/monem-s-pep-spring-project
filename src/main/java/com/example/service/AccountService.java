package com.example.service;

import com.example.repository.AccountRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    public Account getAccountById(Integer id) {
        return accountRepo.findAccountByAccountId(id);
    }

    public Optional<Account> getAccountByUsername(String name) {
        return accountRepo.findAccountByUsername(name);
    }

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account loginAccount(Account account) {
        Optional<Account> toLogin = accountRepo.findAccountByUsername(account.getUsername());
        if (toLogin.isPresent() && toLogin.get().getPassword().equals(account.getPassword())) {
            return toLogin.get();
        }

        return null;
    }

}
