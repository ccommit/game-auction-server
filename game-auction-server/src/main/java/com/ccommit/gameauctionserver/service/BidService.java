package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.ResponseItemToBid;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BidService {

    private BidMapper bidMapper;
    private ItemMapper itemMapper;

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

    public List<ResponseItemToBid> searchItemsToBid(BidSearchFilter bid) {
        return bidMapper.searchBidToItem(bid);
    }
}
