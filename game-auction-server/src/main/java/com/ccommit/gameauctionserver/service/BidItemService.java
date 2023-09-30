package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.mapper.BidItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BidItemService {

    private BidItemMapper bidItemMapper;

    // TODO : Exception 발생시 Rollback 처리 (issold,limit_time)
    @Transactional
    public void instantBuyItem(String bidId, String userId) {
        User user = bidItemMapper.getUserInfoFromId(userId);

        Bid bid = bidItemMapper.getBidInfoFromId(Integer.parseInt(bidId));


        if(checkUserGold(user.getGold(), bid.getPrice()))
        {
            transactionBuyInstantItem(bid.getItemId(), user.getId(), bid.getId(), bid.getSellerId(), bid.getPrice());
        }
    }

    public boolean checkUserGold(int userGold, int bidPrice) {
        if (userGold >= bidPrice) {
            return true;
        } else {
            return false;
        }
    }

    public void transactionBuyInstantItem(int itemId, int userId, int bidId, String sellerId, int priceGold) {

       bidItemMapper.updateItemByUserId(itemId, userId);
       bidItemMapper.updateBuyerUserGold(userId, priceGold);
       bidItemMapper.updateSellerUserGold(sellerId, priceGold);
       bidItemMapper.deleteItemToBid(bidId);
    }
}
