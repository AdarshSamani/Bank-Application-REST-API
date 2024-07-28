package com.bank.SafeBank.repo;

import com.bank.SafeBank.dto.AccountDto;
import com.bank.SafeBank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {
    List<Account> searchByNameContainingIgnoreCase(String name);
}
