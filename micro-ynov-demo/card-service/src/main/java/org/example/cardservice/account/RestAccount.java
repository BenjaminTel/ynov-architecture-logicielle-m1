package org.example.cardservice.account;

import org.example.cardservice.dto.AccountDto;
import org.example.cardservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestAccount {

    @Autowired
    private RestTemplate restTemplate;

    private final String ACCOUNT_URL = "http://localhost:8080/accounts";

    public AccountDto getAccountById(Long id) {
        try {
            return restTemplate.getForObject(ACCOUNT_URL + "/" + id, AccountDto.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("Account with id " + id + " not found");
        }
    }
}
