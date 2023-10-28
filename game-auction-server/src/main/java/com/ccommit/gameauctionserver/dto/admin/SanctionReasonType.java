package com.ccommit.gameauctionserver.dto.admin;

import lombok.Getter;

@Getter
public enum SanctionReasonType {

    USER_EMBEZZLEMENT("명의 도용 의심 유저"),
    USER_UNSANCTION("탈퇴로 인한 제재 종료"),
    ITEM_COPY("복사된 아이템"),
    ITEM_UNSANCTION("해당 아이템 경매장 재등록");

    private final String message;
    SanctionReasonType(String message){
        this.message = message;
    }
}
