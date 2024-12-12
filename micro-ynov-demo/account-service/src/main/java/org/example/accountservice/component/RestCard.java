package org.example.accountservice.account;


import org.example.accountservice.dto.CardDto;
import org.example.accountservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestCard {

    @Autowired
    private RestTemplate restTemplate;

    private final String CARD_URL = "http://localhost:8080/cards";

    public List<CardDto> getCardsByAccountId(Long id) {
        try {
            return restTemplate.getForObject(CARD_URL + "/accounts/" + id, List.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("Account with id " + id + " not found");
        }
    }
}
