package com.ccommit.gameauctionserver.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class MyResentTrade {
    private int id;
    private int price;
    private int itemId;

    private String sellerId;
    private Date saleTime;
}
