package com.ccommit.gameauctionserver.dto;

import com.ccommit.gameauctionserver.dto.item.ItemMainCategory;
import com.ccommit.gameauctionserver.dto.item.ItemRating;
import com.ccommit.gameauctionserver.dto.item.ItemSubCategory;
import lombok.Getter;

@Getter
public class Item {

    private int id;
    private String name;
    private int equipmentLevel;

    private ItemRating rating;
    private ItemMainCategory mainType;
    private ItemSubCategory subType;

    private int property1;
    private int property2;

    private String itemImageURL;
    private int userId;
    private boolean exist;

}
