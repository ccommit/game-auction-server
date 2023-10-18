package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.MyResentTrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyResentMapper {

    public void insertMyResentTradeWithBid(List<Bid> myResentTradeWithBidList);

    public List<MyResentTrade> readMyResentTrade(String userId);

    public void deleteAllMyResentTrade(int userid);
}
