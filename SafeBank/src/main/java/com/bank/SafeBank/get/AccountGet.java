package com.bank.SafeBank.get;

import com.bank.SafeBank.model.Account;
import com.bank.SafeBank.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountGet {
    private AccountRepo repo;

    @Autowired
    public AccountGet(AccountRepo repo) {
        this.repo = repo;
    }

    public Account account(long id){
        return repo.findById(id).orElseThrow(()->new RuntimeException("No account found with id "+id));
    }
}
