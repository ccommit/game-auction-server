package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.MyResentTrade;
import com.ccommit.gameauctionserver.mapper.MyResentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyResentTradeService {

    @Autowired
    MyResentMapper myResentMapper;

    public List<MyResentTrade> loadMyResentTrade(String userId)
    {
        List<MyResentTrade> myResentTradeList = new ArrayList<>();
        myResentTradeList = myResentMapper.readMyResentTrade(userId);

        return myResentTradeList;
    }
}
