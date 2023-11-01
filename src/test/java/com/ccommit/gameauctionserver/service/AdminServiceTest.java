package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.SanctionHistoryDTO;
import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.admin.SanctionReasonType;
import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.mapper.AdminMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {


    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminService adminService;

    User sanctionUser;
    Bid sanctionBidWithItem;

    @BeforeEach
    public void createAdmin()
    {
        sanctionUser = new User();
        sanctionUser.setId(1);
        sanctionUser.setUserType(UserType.USER);
        sanctionUser.setUserId("TestAbusingUserID");
        sanctionUser.setPassword("TestAbusingUserPW");
        sanctionUser.setNickname("TestAbusingUserNick");
        sanctionUser.setPhoneNumber("010-1111-2222");
        sanctionUser.setUserLevel(10);
        sanctionUser.setGold(0);

        sanctionBidWithItem = new Bid();
        sanctionBidWithItem.setId(1);
        sanctionBidWithItem.setBidStatus(BidStatus.SALE);
        sanctionBidWithItem.setPrice(1000);
        sanctionBidWithItem.setStartPrice(100);
        sanctionBidWithItem.setSellerId("TestSellerID");
        sanctionBidWithItem.setItemId(2);
    }

    @Test
    @DisplayName("특정 아이디의 유저를 제재하고 제재사유를 저장한다")
    @Transactional
    public void updateUserTypeTest()
    {
        doNothing().when(adminMapper).updateUserType(sanctionUser.getUserId(),UserType.ABUSER);

        SanctionHistoryDTO resultSanctionHistoryDTO = adminService.updateUserType("TestAdminId", sanctionUser.getUserId(), UserType.ABUSER);

        assertEquals(resultSanctionHistoryDTO.getReasonType(),SanctionReasonType.USER_EMBEZZLEMENT.getMessage());

        //times : 해당하는 메서드의 호출이 몇번 되었는지 확인한다. 기대값과 다르면 테스트 실패
        verify(adminMapper, times(1)).updateUserType(any(String.class),any(UserType.class));
        verify(adminMapper, times(1)).insertSanctionHistory(any(SanctionHistoryDTO.class));
    }

    @Test
    @DisplayName("경매장에 등록된 특정 아이템을 제재하며 제재사유를 저장한다")
    @Transactional
    public void updateBidStatus()
    {
        doNothing().when(adminMapper).updateBidStatus(sanctionBidWithItem.getId(), BidStatus.SALE_SANCTION);

        SanctionHistoryDTO resultSanctionHistoryDTO = adminService.updateBidStatus("TestAdminId",sanctionBidWithItem.getId(),BidStatus.SALE_SANCTION);

        assertEquals(resultSanctionHistoryDTO.getReasonType(),SanctionReasonType.ITEM_COPY.getMessage());

        verify(adminMapper, times(1)).updateBidStatus(any(int.class),any(BidStatus.class));
        verify(adminMapper, times(1)).insertSanctionHistory(any(SanctionHistoryDTO.class));
    }

}
