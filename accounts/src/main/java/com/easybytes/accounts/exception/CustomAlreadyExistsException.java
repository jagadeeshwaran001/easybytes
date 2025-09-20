package com.easybytes.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomAlreadyExistsException extends RuntimeException{

    public CustomAlreadyExistsException(String message) {
        super(message);
    }
}
