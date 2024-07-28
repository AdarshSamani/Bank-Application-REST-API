package com.bank.SafeBank.controller;

import com.bank.SafeBank.dto.AccountDto;
import com.bank.SafeBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    // Add Account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        AccountDto savedAccountDto = service.addAccount(accountDto);
        return new ResponseEntity<>(savedAccountDto, HttpStatus.CREATED);
    }

    // Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable long id){
        AccountDto accountDto = service.getAccount(id);
        return ResponseEntity.ok(accountDto);
    }

    // Deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable long id,@RequestBody Map<String,Double> request){
        double amount = request.get("amount");
        AccountDto accountDto = service.deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Withdraw REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable long id,@RequestBody Map<String,Double> request){
        double amount = request.get("amount");
        AccountDto accountDto = service.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accountDtoAll = service.getAllAccounts();
        return ResponseEntity.ok(accountDtoAll);
    }

    // Delete Account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long id){
        service.deleteAccount(id);
        return ResponseEntity.ok("Deleted id "+id+" successfully");
    }

    // Search Account By Name REST API
    @PostMapping("/searchName")
    public ResponseEntity<List<AccountDto>> searchByName(@RequestBody Map<String,String> request){
        String name = request.get("keyword");
        List<AccountDto> accountDtoAll = service.searchByNameOrId(name);
        return ResponseEntity.ok(accountDtoAll);
    }

    // Transfer REST API
    @PutMapping("/transfer/from/{id1}/to/{id2}")
    public ResponseEntity<List<AccountDto>> transfer(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @RequestBody Map<String,Double> request){
        List<AccountDto> accountDtoAll = service.transfer(id1, id2, request.get("amount"));
        return ResponseEntity.ok(accountDtoAll);
    }
}
