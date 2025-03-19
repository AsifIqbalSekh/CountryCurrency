package com.asifiqbalsekh.demo.CountryCurrencyAPI.util;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.DuplicateEntryException;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.GlobalExceptionResponse;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandeller {

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleRuntimeExceptions(DuplicateEntryException ex) {

        var response= new GlobalExceptionResponse(ex.getMessage(), CommonMethod.getTimestamp(), ex.getStatus().value());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message= ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList().toString();
        var response= new GlobalExceptionResponse(message,CommonMethod.getTimestamp(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleException(Exception ex) {

        var response= new GlobalExceptionResponse(ex.getMessage(),CommonMethod.getTimestamp(),
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
