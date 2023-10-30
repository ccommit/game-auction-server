package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {

    void createItem(Item item);

}
