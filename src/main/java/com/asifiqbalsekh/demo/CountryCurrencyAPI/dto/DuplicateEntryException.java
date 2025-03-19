package com.asifiqbalsekh.demo.CountryCurrencyAPI.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateEntryException extends RuntimeException {
    HttpStatus status;
    public DuplicateEntryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
