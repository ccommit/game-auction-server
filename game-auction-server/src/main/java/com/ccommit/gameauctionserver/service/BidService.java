package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.ResponseItemToBid;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    @Autowired
    private BidMapper bidMapper;

    public boolean isExistItemId(int itemId)
    {
        return bidMapper.isExistItemId(itemId);
    }

    public void registrationItem(Bid bid)
    {
        bidMapper.registrationItem(bid);
    }

    public Bid readLastItemToBid()
    {
        return bidMapper.readLastItemToBid();
    }


    public List<ResponseItemToBid> searchItemsToBid(BidSearchFilter bid)
    {
        return bidMapper.searchBidToItem(bid);
    }
}
