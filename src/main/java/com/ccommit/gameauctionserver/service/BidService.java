package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dao.BidItemDAO;
import com.ccommit.gameauctionserver.dao.HistoryBidDAO;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import com.ccommit.gameauctionserver.dto.bid.BidWithUserDTO;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import com.ccommit.gameauctionserver.utils.BidMQProducer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class BidService {

    private BidMapper bidMapper;
    private ItemMapper itemMapper;
    private UserMapper userMapper;
    private BidItemDAO bidItemDAO;
    private HistoryBidDAO historyBidDAO;
    private BidMQProducer bidMQProducer;


    public void isExistItemId(int itemId) {
        if (bidMapper.isExistItemId(itemId) != null) {
            log.info(ErrorCode.ITEM_DUPLICATED.getMessage());
            throw new CustomException(ErrorCode.ITEM_DUPLICATED);
        }
    }

    public void isUserItemId(int itemId, String userId) {
        if (itemMapper.isUserItemId(itemId, userId) == null) {
            log.info(ErrorCode.ITEM_FORBIDDEN.getMessage());
            throw new CustomException(ErrorCode.ITEM_FORBIDDEN);
        }
    }

    public void registrationItem(Bid bid, String userId) {
        isExistItemId(bid.getItemId());
        isUserItemId(bid.getItemId(), userId);

        bid.setSellerId(userId);
        bidMapper.registrationItem(bid);
    }
    /** @Transactional : 비즈니스 로직이 트랜잭션 처리를 필요로 할때 사용하는 어노테이션으로 내부적으로 AOP를 통해 트랜잭션을 프록시 방식으로 동작합니다.
     * AOP 형식 : JDK Dynamic Proxy (인터페이스 확장시에), CGLIB (인터페이스 사용하지 않을 시)
     * JDBC에서 필요한 dataSource.getConnection(), connection.setAutoCommit, coonection.coomit, coonection.rollback 등
     * 트랜잭션시의 필요한 공통적인 관심사를 @Transactional를 통해 관리할 수 있습니다.
     * 또한 Attribute를 설정하여 롤백범위, 트랜잭션 전파, 트랜잭션 격리수준 등을 설정할 수 있습니다.
     * (propagation = Progation.REQUIRED등) : 트랜잭션 전파를 설정할 때 사용합니다.
     * (isolation = Isolation.REPEATABLE_READ등) :  격리수준을 설정할 때 사용합니다.
     * (noRollbackFor,rollbackFor) : 특정 예외가 발생하는 클래스에 대해 롤백 수행을 정의합니다.
     * (timeout=-1) : 메서드 수행 시간을 설정합니다. (-1는 무제한, 기본값)
     * (readOnly) : 트랜잭션을 읽기 전용으로 사용합니다. 읽기 만 할 경우 처리속도를 높이기 위해 사용합니다.
    * */
    @Transactional
    public Bid updateItemWithBid(int bidId, String userId, int priceGold) {

        Bid bidItem = bidItemDAO.readBidWithCache(bidId);
        RequestUserInfo userInfo = userMapper.readUserInfo(userId);

        if (bidItem == null || !(bidItem.getBidStatus().equals(BidStatus.SALE))) {
            log.info(ErrorCode.ITEM_FORBIDDEN.getMessage());
            throw new CustomException(ErrorCode.ITEM_FORBIDDEN);
        } else if (bidItem.getSellerId().equals(userId) || userId.equals(bidItem.getHighestBidderId())) {
            log.info(ErrorCode.BID_AUTHORITY.getMessage());
            throw new CustomException(ErrorCode.BID_AUTHORITY);
        } else if (bidItem.getPresentPrice() >= priceGold || bidItem.getPrice() < priceGold
                || userInfo.getGold() < priceGold) {
            log.info(ErrorCode.BID_CREDIT_CANCLED.getMessage());
            throw new CustomException(ErrorCode.BID_CREDIT_CANCLED);
        }

        BidWithUserDTO bidWithUserDTO = BidWithUserDTO.builder()
                .bid(bidItem)
                .userInfo(userInfo)
                .pirceGold(priceGold)
                .build();
        try {
            bidMQProducer.ProduceBidData(bidWithUserDTO);
        }
        catch (MyBatisSystemException e) {

                historyBidDAO.insertHistoryUpdateBid(bidWithUserDTO);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error(ErrorCode.SERVER_INTERNAL.getMessage(),"MyBatis Server Error");
                throw new CustomException(ErrorCode.SERVER_INTERNAL);
        }  catch (Exception e){
            log.error(e.getMessage());
        }
        bidItem.setPresentPrice(priceGold);
        bidItem.setHighestBidderId(userId);
        bidItem.setBidStatus(priceGold == bidItem.getPrice() ? BidStatus.SOLD_OUT : BidStatus.SALE);
        return bidItemDAO.UpdateCacheData(bidItem);
    }

    public Bid readLastItemToBid() {
        return bidMapper.readLastItemToBid();
    }

    public List<Bid> searchItemsToBid(BidSearchFilter bid) {
        try {
            return bidMapper.searchBidData(bid);
        } catch (Exception e) {
            log.error(e.getMessage(), "SearchException");
            throw e;
        }
    }
}
