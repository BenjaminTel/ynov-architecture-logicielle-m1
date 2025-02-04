package com.ubik.formation.borrowingservice.component;

import com.ubik.formation.borrowingservice.dto.BookDto;
import com.ubik.formation.borrowingservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestBook {

    @Autowired
    private RestTemplate restTemplate;
    private final String BOOK_URL = "http://localhost:8081/books/";

    public BookDto getBookById(Long id) {
        try {
            return restTemplate.getForObject(BOOK_URL + "/" + id, BookDto.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }
    }

    public void returnBookById(Long id) {
        try {
            restTemplate.getForObject(BOOK_URL + "/return/" + id, BookDto.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }
    }

}
