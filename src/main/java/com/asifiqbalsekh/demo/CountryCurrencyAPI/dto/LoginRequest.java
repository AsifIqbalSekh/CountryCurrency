package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "username can't be null")
        String username,
        @NotNull(message = "password can't be null")
        String password
) {
}
