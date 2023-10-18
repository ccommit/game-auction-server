package com.ccommit.gameauctionserver.exception;

public class CustomException extends RuntimeException{

    private final ErrorCode exceptionCode;
    public CustomException (ErrorCode exceptionCode)
    {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ErrorCode getErrorCode()
    {
        return exceptionCode;
    }

}
