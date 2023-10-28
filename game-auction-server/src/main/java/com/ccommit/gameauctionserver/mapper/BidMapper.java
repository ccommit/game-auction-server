package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.BidWithUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BidMapper {

    Integer isExistItemId(int itemId);

    void registrationItem(Bid bid);

    Bid readLastItemToBid();

    Bid readBidWithItemID(int id);

    void updateUserGold(String userId, int priceGold);

    void updateInstantBid(BidWithUserDTO bidWithUserDTO);

    List<Bid> searchBidData(BidSearchFilter bidSearchFilter);
}
