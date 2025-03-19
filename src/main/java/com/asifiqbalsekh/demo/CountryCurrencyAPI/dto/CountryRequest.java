package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CountryRequest(

        @NotNull(message = "country_name can't be null")
        String country_name,
        @NotNull(message = "Country_code can't be null")
        @Size(min = 2, max = 8, message = "currency_code must be 2-8 characters long")
        String currency_code,
        @NotNull(message = "currency_name can't be null")
        String currency_name) {
}
