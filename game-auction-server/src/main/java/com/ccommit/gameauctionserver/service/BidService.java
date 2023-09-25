package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

    @Autowired
    private BidMapper bidMapper;

    public void registrationItem(Bid bid)
    {
        bidMapper.registrationItem(bid);
    }

    public void deleteItemToBid()
    {
        bidMapper.deleteItemToBid();
    }

}
