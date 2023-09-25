package com.ccommit.gameauctionserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Bid {
    private int id;
    private Date createTime;
    private Date limitTime;

    private int price;
    private int startPrice;
    private int presentPrice;

    private String highestBidderID;
    private String sellerID;
    private boolean isSold;
    private int itemID;

}
