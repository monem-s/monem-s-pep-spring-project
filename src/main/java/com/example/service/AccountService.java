package com.example.service;

import com.example.repository.AccountRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.DuplicateUserException;
import com.example.exception.UnauthorizedException;

import java.util.Optional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    /**
     * Creates a new account.
     * @param account the account to be created.
     * @return new account if registration was successful.
     * @throws DuplicateUserException if username already exists.
     * @throws ClientErrorException if signup credentials are invalid.
     */
    public Account createAccount(Account account) {
        // Check for existing user
        Optional<Account> dupCheck = accountRepo.findAccountByUsername(account.getUsername());
        if (dupCheck.isPresent()) {
            throw new DuplicateUserException();
        }

        // Check username and password
        if (account.getUsername().length() < 1 || account.getPassword().length() < 4) {
            throw new ClientErrorException();
        }

        return accountRepo.save(account);
    }

    /**
     * Logs into account.
     * @param account the account to be logged into.
     * @return account if login was successful.
     * @throws UnauthorizedException if account not found or credentials invalid.
     */
    public Account loginAccount(Account account) {
        Optional<Account> toLogin = accountRepo.findAccountByUsername(account.getUsername());
        if (toLogin.isPresent() && toLogin.get().getPassword().equals(account.getPassword())) {
            return toLogin.get();
        }

        throw new UnauthorizedException();
    }

    /**
     * Retrieves account by ID.
     * @param id the ID of the account.
     * @return account if id was found.
     */
    public Account getAccountById(Integer id) {
        Optional<Account> acc = accountRepo.findAccountByAccountId(id);
        if (acc.isPresent()) {
            return acc.get();
        }

        return null;
    }

    /**
     * Retrieves account by username.
     * @param name the username of the account.
     * @return account if username was found.
     */
    public Account getAccountByUsername(String name) {
        Optional<Account> acc = accountRepo.findAccountByUsername(name);
        if (acc.isPresent()) {
            return acc.get();
        }

        return null;
    }
}
