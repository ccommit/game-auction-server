package com.ccommit.gameauctionserver.dto.bid;

import lombok.Getter;

@Getter
public class ResponseItemToBid {
    private int id;

    private String name;
    private int equipmentLevel;
    private int property1;
    private int property2;

    private int price;
    private int presentPrice;

    // TODO :
    /*
    BifSearchFilter의 조건에 따른 검색 후
    그에 맞는 응답을 하기위해 만든 DTO클래스입니다.
    mapper.xml이 정상 작동되는지 및 5번 이슈를 만들기 위해 몇가지의 변수만 선언하였으며,
    추가적으로 몇가지의 변수들을 추가할 예정입니다.
    또한 int 타입은 null값을 받지 않아 integer타입으로 변환을 고려중입니다.
    */
}
