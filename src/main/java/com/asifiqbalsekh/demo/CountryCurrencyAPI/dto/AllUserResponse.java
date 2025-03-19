package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import java.util.List;
import java.util.Set;

public record AllUserResponse(
        String fullname,
        String email,
        boolean activeStatus,
        Set<Roles> roles

) {
}
