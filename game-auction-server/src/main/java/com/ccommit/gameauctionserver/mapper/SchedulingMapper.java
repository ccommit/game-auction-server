package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulingMapper {
    void schedulingUpdateBidInfo(Bid bid);

    void schedulingEndBid();

    void schedulingDelete();
}
