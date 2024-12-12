package org.example.cardservice.service.impl;

import org.example.cardservice.account.RestAccount;
import org.example.cardservice.dto.AccountDto;
import org.example.cardservice.entity.Card;
import org.example.cardservice.exception.ResourceNotFoundException;
import org.example.cardservice.repository.CardRepository;
import org.example.cardservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RestAccount restAccount;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public List<Card> getCardsByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    public Card saveCard(Card card) {
        AccountDto accountDto = restAccount.getAccountById(card.getAccountId());
        if (accountDto == null) {
            throw new ResourceNotFoundException("Account not found");
        }
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
