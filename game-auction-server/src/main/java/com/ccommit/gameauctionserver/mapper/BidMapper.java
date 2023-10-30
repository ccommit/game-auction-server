package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;

import java.util.List;

public interface BidMapper {

    void registrationItem(Bid bid);

    Bid readLastItemToBid();

    List<Bid> readUserItemsToBid(String userId);

    void deleteItemToBid();
}
