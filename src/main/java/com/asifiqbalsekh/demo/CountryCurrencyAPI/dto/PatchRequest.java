package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatchRequest(
        @NotNull(message = "country_name can't be null")
        String country_name,

        String currency_code,
        String currency_name
) { }
