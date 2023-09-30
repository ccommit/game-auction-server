package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.User;

public interface BidItemMapper {

    User getUserInfoFromId(String userId);
    Bid getBidInfoFromId(int bidId);

    void updateItemByUserId(int itemId, int userId);
    void updateBuyerUserGold(int userId, int priceGold);

    void updateSellerUserGold(String sellerId, int priceGold);
    void deleteItemToBid(int bidId);


}
