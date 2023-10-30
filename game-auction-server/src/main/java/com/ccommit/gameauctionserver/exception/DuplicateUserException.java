package com.ccommit.gameauctionserver.exception;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String message)
    {
        super(message);
    }
}
