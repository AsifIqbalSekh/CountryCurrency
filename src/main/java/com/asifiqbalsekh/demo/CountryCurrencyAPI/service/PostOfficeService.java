package com.asifiqbalsekh.demo.CountryCurrencyAPI.service;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.PostalSecureRestTemplateResponse;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.TestRestTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PostOfficeService {
    private final RestTemplate restTemplate;

    @Value("${external.api.test}")
    private String testUrl;

    @Value("${external.api.test_secure_post}")
    private String postSecureUrl;

    @Value("${external.api.test_secure_post_token}")
    private String postSecureToken;


    public TestRestTemplateResponse getTestRestTemplateData() {

        return restTemplate.exchange(
                testUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TestRestTemplateResponse>() {}
        ).getBody();

    }

    public ResponseEntity<PostalSecureRestTemplateResponse> getPostSecureCity(String postalCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", postSecureToken);
        HttpEntity<Object> entity=new HttpEntity<Object>(headers);
        try {
            return restTemplate.exchange(
                    postSecureUrl + postalCode,
                    HttpMethod.GET,
                    entity,
                    PostalSecureRestTemplateResponse.class
            );
        }
        catch(Exception e) {
            throw new RuntimeException("Error during postal service secure api call: " + e);
        }
    }
}
