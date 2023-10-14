package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;

import java.util.List;

public interface BidMapper {

    Integer isExistItemId(int itemId);
    void registrationItem(Bid bid);

    Bid readLastItemToBid();

    Bid readBidWithItemID(int id);

    List<Bid> searchBidData(BidSearchFilter bidSearchFilter);

}
