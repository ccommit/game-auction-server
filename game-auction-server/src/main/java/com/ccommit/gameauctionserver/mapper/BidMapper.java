package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.ResponseItemToBid;

import java.util.List;

public interface BidMapper {

    Integer isExistItemId(int itemId);

    void registrationItem(Bid bid);

    Bid readLastItemToBid();

    List<ResponseItemToBid> searchBidToItem(BidSearchFilter bidSearchFilter);

    void deleteItemToBid();
}
