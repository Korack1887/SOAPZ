package com.example.SOAPZ.exception;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Could not find ticket " + id);
    }
}