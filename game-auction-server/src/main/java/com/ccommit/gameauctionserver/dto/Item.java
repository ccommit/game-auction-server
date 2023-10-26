package com.ccommit.gameauctionserver.dto;

import com.ccommit.gameauctionserver.dto.item.ItemMainCategory;
import com.ccommit.gameauctionserver.dto.item.ItemRating;
import com.ccommit.gameauctionserver.dto.item.ItemSubCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Item {

    private int id;
    private String name;
    private int equipmentLevel;

    private ItemRating rating;
    private ItemMainCategory mainType;
    private ItemSubCategory subType;

    private int firstProperty;
    private int secondProperty;

    private String itemImageURL;
    private int userId;
    private boolean exist;

}
