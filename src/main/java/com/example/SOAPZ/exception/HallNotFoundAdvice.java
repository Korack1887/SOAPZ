package com.example.SOAPZ.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class HallNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(HallNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String hallNotFoundHandler(HallNotFoundException ex) {
        return ex.getMessage();
    }
}
