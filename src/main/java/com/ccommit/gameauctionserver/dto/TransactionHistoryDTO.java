package com.ccommit.gameauctionserver.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class TransactionHistoryDTO {
    private int id;
    private int tradePrice;
    private int itemId;

    private String sellerId;
    private String buyerId;
    private Date saleTime;
}
