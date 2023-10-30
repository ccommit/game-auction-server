package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    @Autowired
    private BidMapper bidMapper;

    public void registrationItem(Bid bid)
    {
        bidMapper.registrationItem(bid);
    }

    public Bid readLastItemToBid()
    {
        return bidMapper.readLastItemToBid();
    }

    public List<Bid> readUserItemsToBid(String userId)
    {
        return bidMapper.readUserItemsToBid(userId);
    }
    public void deleteItemToBid()
    {
        bidMapper.deleteItemToBid();
    }

}
