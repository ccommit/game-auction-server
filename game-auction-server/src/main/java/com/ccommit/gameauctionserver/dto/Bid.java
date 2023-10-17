package com.ccommit.gameauctionserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Bid {

    private int id;
    private Date createTime;
    private Date limitTime;

    private int price;
    private int startPrice;
    private int presentPrice;

    private String highestBidderId;
    private String sellerId;
    private boolean isSold;
    private int itemId;

}
