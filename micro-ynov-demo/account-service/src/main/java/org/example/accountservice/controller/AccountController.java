package org.example.accountservice.controller;


import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

}