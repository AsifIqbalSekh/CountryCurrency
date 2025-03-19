package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RolesRequest(
        @NotNull(message = "email can't be null")
        String email,
        @NotNull(message = "req_roles can't be null")
        List<Integer> req_roles

) {
}
