package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BidMapper {

    Integer isExistItemId(int itemId);

    void registrationItem(Bid bid);

    Bid readLastItemToBid();

    Bid readBidWithItemID(int id);


    void updateUserGold(String userId, int priceGold);

    void updateInstantBid(int userId, int bidId);


    List<Bid> searchBidData(BidSearchFilter bidSearchFilter);
}
