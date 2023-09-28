package com.ccommit.gameauctionserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandlers {

    @ExceptionHandler(DuplicateUserException.class)
    private ResponseEntity handleDuplicateUserException(DuplicateUserException ex) {

        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
