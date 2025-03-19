package com.asifiqbalsekh.demo.CountryCurrencyAPI.controller;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.PostalSecureRestTemplateResponse;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.TestRestTemplateResponse;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post-office")
public class PostOfficeController {
    private final PostOfficeService postOfficeService;

    @GetMapping("/testing")
    public ResponseEntity<TestRestTemplateResponse> getPostOffice() {

        return new ResponseEntity<>(postOfficeService.getTestRestTemplateData(), HttpStatusCode.valueOf(203));

    }
    @GetMapping("/secure/{postalCode}")
    public ResponseEntity<PostalSecureRestTemplateResponse> getPostOfficeByPostalCode(@PathVariable String postalCode) {
        return postOfficeService.getPostSecureCity(postalCode);
    }
}
