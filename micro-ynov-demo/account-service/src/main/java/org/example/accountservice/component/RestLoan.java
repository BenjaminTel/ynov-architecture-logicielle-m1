package org.example.accountservice.component;

import org.example.accountservice.dto.LoanDto;
import org.example.accountservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestLoan {

    @Autowired
    private RestTemplate restTemplate;

    private final String LOAN_BASE_URL = "http://localhost:8080/loans";
    public List<LoanDto> getLoanByAccountId(Long id) {
        try {
            return restTemplate.getForObject(LOAN_BASE_URL + "/accounts/" + id, List.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("Loans with account_id " + id + " not found");
        }
    }
}
