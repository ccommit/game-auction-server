package com.ccommit.gameauctionserver.dto.bid;

import com.ccommit.gameauctionserver.dto.item.ItemMainCategory;
import com.ccommit.gameauctionserver.dto.item.ItemRating;
import com.ccommit.gameauctionserver.dto.item.ItemSubCategory;
import lombok.Getter;

@Getter
public class BidSearchFilter {

    private String itemName;

    private Integer minLevel;
    private Integer maxLevel;

    private Integer maxPrice;
    private Integer minFirstProperty;
    private Integer minSecondProperty;

    private ItemRating itemRating;
    private ItemMainCategory mainCategory;
    private ItemSubCategory subCategory;

    private BidSortingType bidSortingType;
    private boolean desc;

    private int limitPage = 20;
    private int offset = 0;
}
