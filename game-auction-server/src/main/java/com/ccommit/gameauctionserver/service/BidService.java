package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dao.BidItemDAO;
import com.ccommit.gameauctionserver.dao.ItemDAO;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BidService {

    private BidMapper bidMapper;
    private ItemMapper itemMapper;
    private BidItemDAO bidItemDAO;
    private ItemDAO itemDAO;

    public void isExistItemId(int itemId) {
        if (bidMapper.isExistItemId(itemId) != null) {
            throw new CustomException(ErrorCode.ITEM_DUPLICATED);
        }
    }

    private void isUserItemId(int itemId, String userId) {
        if (itemMapper.isUserItemId(itemId, userId) == null) {
            throw new CustomException(ErrorCode.ITEM_FORBIDDEN);
        }
    }

    public void registrationItem(Bid bid, String userId) {
        isExistItemId(bid.getItemId());
        isUserItemId(bid.getItemId(), userId);

        bid.setSellerId(userId);
        bidMapper.registrationItem(bid);
    }

    public Bid readLastItemToBid() {
        return bidMapper.readLastItemToBid();
    }

    public List<Pair<Bid,Item>> searchItemsToBid(BidSearchFilter bid) {

        List<Bid> bidList = bidMapper.searchBidData(bid);
        List<Pair<Bid,Item>> pairs = new ArrayList<>();

        for(Bid bids : bidList)
        {
            Bid resultBid = bidItemDAO.readBidWithCache(bids.getId());
            Item resultItem = itemDAO.readItemWithCache(bids.getItemId());

            Pair<Bid,Item> pair = Pair.of(resultBid,resultItem);
            pairs.add(pair);
        }

        return pairs;
    }
}
