package com.example.springboot1er.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${users.api.url}")
    private String usersApiUrl;

    public boolean userExists(UUID userId) {
        try {
            Boolean result = restTemplate.getForObject(
                    usersApiUrl + "/users/" + userId + "/valid",
                    Boolean.class
            );
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            return false;
        }
    }
}