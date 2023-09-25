package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;

public interface BidMapper {

    void registrationItem(Bid bid);

    void deleteItemToBid();
}
