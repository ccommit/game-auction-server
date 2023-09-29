package com.ccommit.gameauctionserver.dto.bid;

import com.ccommit.gameauctionserver.dto.item.ItemMainCategory;
import com.ccommit.gameauctionserver.dto.item.ItemRating;
import com.ccommit.gameauctionserver.dto.item.ItemSubCategory;
import lombok.Getter;

@Getter
public class BidSearchFilter {

    private final int LIMIT_PAGE = 20;
    private final int OFFSET = 0;

    private String itemName;

    private int minLevel;
    private int maxLevel;

    private int maxPrice;
    private int minProperty1;
    private int minProperty2;

    private ItemRating itemRating;
    private ItemMainCategory mainCategory;
    private ItemSubCategory subCategory;

    private BidSortingType bidSortingType;

    private int limitPage = LIMIT_PAGE;
    private int offset = OFFSET;
}
