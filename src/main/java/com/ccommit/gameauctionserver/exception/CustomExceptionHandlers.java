package com.ccommit.gameauctionserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandlers {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<?> handCustomException(CustomException ex)
    {
//      return new ResponseEntity<>(ex.getErrorCode().getMessage(), HttpStatusCode.valueOf(ex.getErrorCode().getCode()));
        return new ResponseEntity<>((ex.getErrorCode().getCode()+" , "+ex.getErrorCode().getMessage()),
                HttpStatus.OK);

    }

/*    @ExceptionHandler(CustomException.class)
    private ApiResponse<?> handCustomException(CustomException ex)
    {
        return ApiResponse.createError(ex.getErrorCode().getMessage(), ex.getErrorCode().getCode());
    }*/
}
