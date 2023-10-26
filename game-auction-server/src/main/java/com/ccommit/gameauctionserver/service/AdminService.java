package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.SanctionHistoryDTO;
import com.ccommit.gameauctionserver.dto.admin.SanctionReasonType;
import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.mapper.AdminMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AdminService {

    private AdminMapper adminMapper;

    @Transactional
    public SanctionHistoryDTO updateUserType(String adminId, String userId, UserType userType)
    {
        SanctionHistoryDTO historyDTO = SanctionHistoryDTO.builder()
                .userId(userId)
                .adminId(adminId)
                .reasonType(userType.equals(UserType.ABUSER) ? SanctionReasonType.USER_EMBEZZLEMENT.getMessage() : SanctionReasonType.USER_UNSANCTION.getMessage())
                .bidId(null)
                .build();

        adminMapper.updateUserType(userId, userType);
        adminMapper.insertSanctionHistory(historyDTO);

        return historyDTO;
    }

    @Transactional
    public SanctionHistoryDTO updateBidStatus(String adminId, int bidId, BidStatus bidStatus)
    {
        SanctionHistoryDTO historyDTO = SanctionHistoryDTO.builder()
                .userId(null)
                .adminId(adminId)
                .reasonType(bidStatus.equals(BidStatus.SALE_SANCTION) ? SanctionReasonType.ITEM_COPY.getMessage() : SanctionReasonType.ITEM_UNSANCTION.getMessage())
                .bidId(bidId)
                .build();

        adminMapper.updateBidStatus(bidId, bidStatus);
        adminMapper.insertSanctionHistory(historyDTO);

        return historyDTO;
    }

}
