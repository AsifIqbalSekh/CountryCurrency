package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import jakarta.validation.constraints.NotNull;


public record UserRequest(
        @NotNull(message = "email can't be null")
        String email,
        @NotNull(message = "password can't be null")
        String password,
        @NotNull(message = "fullname can't be null")
        String fullName) {
}
