package com.ccommit.gameauctionserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandlers {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity handCustomException(CustomException ex)
    {
        return new ResponseEntity(ex.getErrorCode().getMessage(),ex.getErrorCode().getStatus());
    }
}
