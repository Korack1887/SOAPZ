package com.example.SOAPZ.exception;

public class HallNotFoundException extends RuntimeException {

    public HallNotFoundException(Long id) {
        super("Could not find hall " + id);
    }
}