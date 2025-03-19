package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record UserResponse(
        String message,
        String email,
        String fullName,
        Set<Roles> role
){

}
