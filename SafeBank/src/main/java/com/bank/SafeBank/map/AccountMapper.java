package com.bank.SafeBank.map;

import com.bank.SafeBank.dto.AccountDto;
import com.bank.SafeBank.model.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(account.getId(), account.getName(), account.getBalance());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(accountDto.getId(), accountDto.getName(), accountDto.getBalance());
        return account;
    }
}
