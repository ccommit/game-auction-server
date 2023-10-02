package com.ccommit.gameauctionserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    ITEM_DUPLICATED(BAD_REQUEST,"해당 아이템은 이미 등록되어 있습니다."),
    ITEM_FORBIDDEN(FORBIDDEN,"해당 아이템은 존재하지 않습니다."),
    ITEM_AUTHORITY(BAD_REQUEST,"해당 아이템은 유저의 소유가 아닙니다."),

    USER_DUPLICATED(BAD_REQUEST,"해당 아이디는 이미 생성되어 있습니다."),
    USER_AUTHORIZATION(BAD_REQUEST,"해당 기능은 로그인이 필요합니다."),
    USER_AUTHORITY(UNAUTHORIZED,"해당 기능의 권한이 없습니다");

    private final HttpStatus status;
    private final String message;
    ErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
