package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.ResponseItemToBid;

import java.util.List;

public interface BidMapper {

    Integer isExistItemId(int itemId);
    void registrationItem(Bid bid);

    Bid readLastItemToBid();
    Bid readBidWithItemID(int id);
    List<ResponseItemToBid> searchBidToItem(BidSearchFilter bidSearchFilter);

    void updateUserGold(String userId, int priceGold);
    void updateInstantBid(int userId, int bidId);

    void schedulingUpdateBidInfo(Bid bid);
    void schedulingEndBid();
    void schedulingDelete();
}
