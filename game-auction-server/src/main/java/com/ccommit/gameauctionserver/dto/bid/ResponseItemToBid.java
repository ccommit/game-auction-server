package com.ccommit.gameauctionserver.dto.bid;

import lombok.Getter;

@Getter
public class ResponseItemToBid {
    private int id;

    private String name;
    private String itemImageURL;

    private int equipmentLevel;
    private int firstProperty;
    private int secondProperty;

    private int price;
    private int presentPrice;
}
