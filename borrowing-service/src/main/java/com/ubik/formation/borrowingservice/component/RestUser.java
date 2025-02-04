package com.ubik.formation.borrowingservice.component;

import com.ubik.formation.borrowingservice.dto.UserDto;
import com.ubik.formation.borrowingservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUser {
    @Autowired
    private RestTemplate restTemplate;
    private final String USER_URL = "http://localhost:8082/users/";

    public UserDto getUserById(Long id) {
        try {
            return restTemplate.getForObject(USER_URL + "/" + id, UserDto.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }
}
