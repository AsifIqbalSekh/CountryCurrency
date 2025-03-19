package com.asifiqbalsekh.demo.CountryCurrencyAPI.controller;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.CountryRequest;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.CountryResponse;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.PatchRequest;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.service.CountryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/country-currency")
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public List<CountryResponse> getAllCountries() {

        log.info("Inside GetAllCountry.....");
        return countryService.listOfAllCountry();
    }

    @PostMapping
    public CountryResponse postCountry(@Valid  @RequestBody CountryRequest countryRequest, HttpServletRequest request) {
        log.info("POST country request: {}", countryRequest);
        return countryService.addCountry(countryRequest,request.getMethod());
    }

    @PutMapping
    public CountryResponse putCountry(@Valid @RequestBody CountryRequest countryRequest,HttpServletRequest request) {
        return countryService.addCountry(countryRequest,request.getMethod());
    }
    @DeleteMapping
    public Map<String,String> deleteCountry(@RequestParam @NotEmpty(message = "countryName can't be Empty")
                                                                                        String countryName){

        return Map.of("message",countryService.deleteCountry(countryName));
    }

    @GetMapping("/{countryName}")
    public CountryResponse getCountry(@PathVariable String countryName) {
        return countryService.getCountry(countryName);
    }

    @PatchMapping
    public CountryResponse patchCountry(@Valid @RequestBody PatchRequest countryRequest) {
        return countryService.updateCountry(countryRequest);
    }


}
