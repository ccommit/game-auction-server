package com.ccommit.gameauctionserver.dto;

import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import lombok.*;

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

    private String highestBidderId;
    private String sellerId;
    private BidStatus bidStatus;
    private int itemId;

}
