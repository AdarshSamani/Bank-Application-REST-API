package com.bank.SafeBank.service.impl;

import com.bank.SafeBank.dto.AccountDto;
import com.bank.SafeBank.get.AccountGet;
import com.bank.SafeBank.map.AccountMapper;
import com.bank.SafeBank.model.Account;
import com.bank.SafeBank.repo.AccountRepo;
import com.bank.SafeBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepo repo;
    private AccountGet get;

    @Autowired
    public AccountServiceImpl(AccountRepo repo, AccountGet get) {
        this.repo = repo;
        this.get = get;
    }

    @Override
    public AccountDto addAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = repo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccount(long id) {
        Account account = get.account(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(long id, double amount) {
        Account account = get.account(id);

        double total = account.getBalance() + amount;
        account.setBalance(total);

        Account savedAccount = repo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(long id, double amount) {
        Account account = get.account(id);

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);

        Account savedAccount = repo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<AccountDto> accountDtoAll = repo.findAll().stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return accountDtoAll;
    }

    @Override
    public void deleteAccount(long id) {
        Account account = get.account(id);
        repo.delete(account);
    }

    @Override
    public List<AccountDto> searchByNameOrId(String name) {
        List<Account> accountAll = repo.searchByNameContainingIgnoreCase(name);
        List<AccountDto> accountDtoAll = accountAll.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return accountDtoAll;
    }

    @Override
    public List<AccountDto> transfer(long id1, long id2, double amount) {
        Account account1 = get.account(id1);
        Account account2 = get.account(id2);

        if(account1.getBalance() < amount){
            throw new RuntimeException("Insufficient balance in id "+id1);
        }

        double payer = account1.getBalance() - amount;
        double payee = account2.getBalance() + amount;
        account1.setBalance(payer);
        account2.setBalance(payee);

        Account savedAccount1 = repo.save(account1);
        Account savedAccount2 = repo.save(account2);

        List<Account> accountAll = Arrays.asList(savedAccount1,savedAccount2);
        List<AccountDto> accountDtoAll = accountAll.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return accountDtoAll;
    }
}
