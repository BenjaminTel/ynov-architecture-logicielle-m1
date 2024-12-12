package org.example.accountservice.service.impl;

import org.example.accountservice.component.RestLoan;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.accountservice.account.RestCard;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestCard restCard;

    @Autowired
    private RestLoan restLoan;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountDto getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Account ac = account.get();
            AccountDto accountDto = new AccountDto();
            accountDto.setId(ac.getId());
            accountDto.setName(ac.getName());
            accountDto.setEmail(ac.getEmail());
            accountDto.setSolde(ac.getSolde());
            accountDto.setLoans(restLoan.getLoanByAccountId(ac.getId()));
            accountDto.setCards(restCard.getCardsByAccountId(ac.getId()));
            return accountDto;
        }
        return null;
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
