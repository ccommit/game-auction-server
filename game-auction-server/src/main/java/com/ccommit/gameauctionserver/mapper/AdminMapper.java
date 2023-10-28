package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.SanctionHistoryDTO;
import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    void updateUserType(String userId, UserType userType);

    void updateBidStatus(int bidId, BidStatus bidStatus);

    void insertSanctionHistory(SanctionHistoryDTO sanctionHistoryDTO);
}
