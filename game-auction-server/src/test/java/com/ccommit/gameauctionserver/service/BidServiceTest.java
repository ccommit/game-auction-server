package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dao.BidItemDAO;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import com.ccommit.gameauctionserver.dto.bid.BidWithUserDTO;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import com.ccommit.gameauctionserver.utils.BidMQProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

    @Mock
    private BidMapper bidMapper;
    @Mock
    private ItemMapper itemMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BidItemDAO bidItemDAO;
    @Mock
    private BidMQProducer bidMQProducer;

    @InjectMocks
    private BidService bidService;

    Bid testItemWithBid;
    Bid exceptionItemWithBid;
    RequestUserInfo requestUserInfo;
    @BeforeEach
    public void createInfo()
    {
        testItemWithBid = new Bid();
        testItemWithBid.setId(55);
        testItemWithBid.setBidStatus(BidStatus.SALE);
        testItemWithBid.setItemId(255);
        testItemWithBid.setPrice(5000);
        testItemWithBid.setStartPrice(500);
        testItemWithBid.setPresentPrice(500);
        testItemWithBid.setSellerId("TestSellerID");

        exceptionItemWithBid = new Bid();
        exceptionItemWithBid.setId(5);
        exceptionItemWithBid.setBidStatus(BidStatus.SOLD_OUT);
        exceptionItemWithBid.setItemId(25);
        exceptionItemWithBid.setPrice(3000);
        exceptionItemWithBid.setStartPrice(500);
        exceptionItemWithBid.setPresentPrice(500);
        exceptionItemWithBid.setSellerId("TestSellerID");

        requestUserInfo = new RequestUserInfo();
        requestUserInfo.setId(10);
        requestUserInfo.setGold(5000);
        requestUserInfo.setUserId("TestBuyerID");
    }

    @Test
    @DisplayName("경매장에 등록되어있는 아이템 ID일경우 예외를 발생합니다.")
    public void isExistItemIdTest()
    {
        when(bidMapper.isExistItemId(1))
                .thenReturn(2);

        assertThrows(CustomException.class, () -> bidService.isExistItemId(1));
    }

    @Test
    @DisplayName("경매장에 등록하려는 아이템이 유저의 소유가 아닌경우 예외를 발생합니다.")
    public void isUserItemIdTest()
    {
        when(itemMapper.isUserItemId(1,"TestUserID"))
                .thenReturn(null);

        assertThrows(CustomException.class, () -> bidService.isUserItemId(1,"TestUserID"));
    }

    @Test
    @DisplayName("로그인된 유저의 아이템을 경매장에 등록합니다.")
    public void registrationItemTest()
    {
        doNothing().when(bidMapper).registrationItem(testItemWithBid);
        when(bidMapper.isExistItemId(testItemWithBid.getItemId()))
                .thenReturn(null);

        bidService.registrationItem(testItemWithBid,testItemWithBid.getSellerId());

        verify(bidMapper).registrationItem(testItemWithBid);
    }

    @Test
    @DisplayName("아이템입찰 예외 : 이미 판매된 상품일 경우 예외 발생")
    public void itemStatusExcpetionTest()
    {
        when(bidItemDAO.readBidWithCache(exceptionItemWithBid.getId()))
                .thenReturn(exceptionItemWithBid);
        when(userMapper.readUserInfo(requestUserInfo.getUserId()))
                .thenReturn(requestUserInfo);

        int testPrice = 5000;

        assertThrows(CustomException.class, () -> bidService.updateItemWithBid(exceptionItemWithBid.getId(),requestUserInfo.getUserId(),testPrice));
    }

    @Test
    @DisplayName("아이템 입찰 예외 : 등록된 상품이 유저의 상품일 경우 예외 발생")
    public void sellerItemExceptionTest()
    {
        when(bidItemDAO.readBidWithCache(testItemWithBid.getId()))
                .thenReturn(testItemWithBid);

        int testPrice = 1000;

        assertThrows(CustomException.class, () -> bidService.updateItemWithBid(testItemWithBid.getId(),testItemWithBid.getSellerId(),testPrice));
    }

    @Test
    @DisplayName("아이템 입찰 예외 : 금액 부족 및 즉시구매가격을 초과한 입력")
    public void priceItemExceptionTest()
    {
        when(bidItemDAO.readBidWithCache(testItemWithBid.getId()))
                .thenReturn(testItemWithBid);
        when(userMapper.readUserInfo(requestUserInfo.getUserId()))
                .thenReturn(requestUserInfo);

        int exceptionPresentPrice = 300;
        int exceptionPrice = 20000;

        assertThrows(CustomException.class, () -> bidService.updateItemWithBid(testItemWithBid.getId(), requestUserInfo.getUserId(),exceptionPresentPrice));
        assertThrows(CustomException.class, () -> bidService.updateItemWithBid(testItemWithBid.getId(), requestUserInfo.getUserId(),exceptionPrice));
    }

    @Test
    @DisplayName("아이템 입찰 성공 : 즉시구매")
    public void instantBuyTest()
    {
        when(bidItemDAO.readBidWithCache(testItemWithBid.getId()))
                .thenReturn(testItemWithBid);
        when(userMapper.readUserInfo(requestUserInfo.getUserId()))
                .thenReturn(requestUserInfo);
        doNothing().when(bidMQProducer).ProduceBidData(any(BidWithUserDTO.class));
        when(bidItemDAO.UpdateCacheData(testItemWithBid))
                .thenReturn(testItemWithBid);

        Bid resultBid = bidService.updateItemWithBid(testItemWithBid.getId(), requestUserInfo.getUserId(),testItemWithBid.getPrice());

        assertEquals(resultBid.getHighestBidderId(),requestUserInfo.getUserId());
        assertEquals(resultBid.getBidStatus(),BidStatus.SOLD_OUT);
    }

    @Test
    @DisplayName("아이템 입찰 성공 : 상위 입찰")
    public void bidBuyTest()
    {
        when(bidItemDAO.readBidWithCache(testItemWithBid.getId()))
                .thenReturn(testItemWithBid);
        when(userMapper.readUserInfo(requestUserInfo.getUserId()))
                .thenReturn(requestUserInfo);
        doNothing().when(bidMQProducer).ProduceBidData(any(BidWithUserDTO.class));
        when(bidItemDAO.UpdateCacheData(testItemWithBid))
                .thenReturn(testItemWithBid);

        int testPrice = 1000;
        Bid resultBid = bidService.updateItemWithBid(testItemWithBid.getId(), requestUserInfo.getUserId(),testPrice);

        assertEquals(resultBid.getPresentPrice(),testPrice);
        assertEquals(resultBid.getHighestBidderId(),requestUserInfo.getUserId());
        assertEquals(resultBid.getBidStatus(),BidStatus.SALE);
    }

    @Test
    @DisplayName("필터를 통한 아이템 결과를 조회합니다.")
    public void searchItemsToBid()
    {
        when(bidMapper.searchBidData(any(BidSearchFilter.class)))
                .thenReturn(new ArrayList<>());

        BidSearchFilter bidSearchFilter = new BidSearchFilter();
        bidService.searchItemsToBid(bidSearchFilter);

        verify(bidMapper).searchBidData(any(BidSearchFilter.class));
    }
}
