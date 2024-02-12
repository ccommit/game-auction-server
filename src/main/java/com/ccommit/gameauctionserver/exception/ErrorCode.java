package com.ccommit.gameauctionserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    ITEM_DUPLICATED(BAD_REQUEST.value(),"해당 아이템은 이미 등록되어 있습니다."),
    ITEM_FORBIDDEN(FORBIDDEN.value(),"해당 아이템은 존재하지 않습니다."),
    ITEM_NOTAUTHORITY(BAD_REQUEST.value(),"해당 아이템은 유저의 소유가 아닙니다."),

    USER_DUPLICATED(BAD_REQUEST.value(),"해당 아이디는 이미 생성되어 있습니다."),
    USER_AUTHORIZATION(BAD_REQUEST.value(),"해당 기능은 로그인이 필요합니다."),
    USER_AUTHORITY(UNAUTHORIZED.value(),"해당 기능의 권한이 없습니다"),
    USER_FORBIDDEN(FORBIDDEN.value(), "해당 아이디는 존재하지 않습니다."),

    BID_AUTHORITY(BAD_REQUEST.value(), "등록된 물품은 사용자의 물품입니다."),
    BID_CREDIT_CANCLED(BAD_REQUEST.value(), "잘못된 금액입니다. 다시 시도해주세요."),

    SERVER_INTERNAL(INTERNAL_SERVER_ERROR.value(), "서버와의 연결이 끊겼습니다.");

    private final int code;
    private final String message;
    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
