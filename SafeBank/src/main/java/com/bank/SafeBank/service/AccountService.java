package com.bank.SafeBank.service;


import com.bank.SafeBank.dto.AccountDto;

import java.util.List;
import java.util.Locale;

public interface AccountService {
    AccountDto addAccount(AccountDto accountDto);
    AccountDto getAccount(long id);
    AccountDto deposit(long id, double amount);
    AccountDto withdraw(long id, double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(long id);
    List<AccountDto> searchByNameOrId(String name);
    List<AccountDto> transfer(long id1, long id2, double amount);
}
