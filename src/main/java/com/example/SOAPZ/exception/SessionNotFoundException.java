package com.example.SOAPZ.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(Long id) {
        super("Could not find session " + id);
    }
}