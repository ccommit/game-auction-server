package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dao.BidItemDAO;
import com.ccommit.gameauctionserver.dao.ItemDAO;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import com.ccommit.gameauctionserver.utils.BidMQProducer;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BidService {

    private BidMapper bidMapper;
    private ItemMapper itemMapper;
    private UserMapper userMapper;
    private BidItemDAO bidItemDAO;
    private ItemDAO itemDAO;
    private BidMQProducer bidMQProducer;


    public void isExistItemId(int itemId) {
        if (bidMapper.isExistItemId(itemId) != null) {
            throw new CustomException(ErrorCode.ITEM_DUPLICATED);
        }
    }

    private void isUserItemId(int itemId, String userId) {
        if (itemMapper.isUserItemId(itemId, userId) == null) {
            throw new CustomException(ErrorCode.ITEM_FORBIDDEN);
        }
    }

    public void registrationItem(Bid bid, String userId) {
        isExistItemId(bid.getItemId());
        isUserItemId(bid.getItemId(), userId);

        bid.setSellerId(userId);
        bidMapper.registrationItem(bid);
    }

    @Transactional
    public Bid updateItemWithBid(int bidId, String userId, int priceGold) {
        Bid bidItem = null;
        try {


            bidItem = bidItemDAO.readBidWithCache(bidId);

            RequestUserInfo userInfo = userMapper.readUserInfo(userId);

            if (bidItem == null || bidItem.isSold()) {
                throw new CustomException(ErrorCode.ITEM_FORBIDDEN);
            } else if (bidItem.getSellerId().equals(userId) || userId.equals(bidItem.getHighestBidderId())) {
                throw new CustomException(ErrorCode.BID_AUTHORITY);
            } else if (bidItem.getPresentPrice() >= priceGold || bidItem.getPrice() < priceGold
                    || userInfo.getGold() < priceGold) {
                throw new CustomException(ErrorCode.BID_CREDIT_CANCLED);
            }

            if (priceGold == bidItem.getPrice()) {
                // 요구사항: queue가 장애시 트랜잭션이 커밋에 실패하여 정상 롤백 후
                // 큐가 재가동되었을떄 이미 데이터를 꺼낸사랑
                updateUserGold(bidItem, userId, priceGold);
                bidMQProducer.ProduceBidData(userInfo.getId(), bidId);
                bidItemDAO.deleteDataWithCache(bidId);
            } else {
                updateUserGold(bidItem, userId, priceGold);
            }

            bidItem.setHighestBidderId(userId);
            bidItem.setPresentPrice(priceGold);
            bidItemDAO.updateBidWithCache(bidId, bidItem);

        } catch (CustomException e) {
//            bidItemDAO.insertHistoryUpdateBid(DTO);
//            // 정상처리되지 않은 입찰정보를 재처리하기위한 테이블
//            // 하루단위 스케줄링 메서드를 만들어서 입찰 실패 ROW 상태가 처리되지 않은 상태면
//            // FOR문을 돌면서 다시 RabbitMQ에 넣어준다.
//            throw UdateItemWithBidException(e.getMessage());
        }

        return bidItem;
    }

    private void updateUserGold(Bid bidItem, String userId, int priceGold) {
        Map<String, Integer> updateUser = new HashMap<>();
        updateUser.put(userId, -priceGold);
        if (bidItem.getHighestBidderId() != null) {
            updateUser.put(bidItem.getHighestBidderId(), bidItem.getPresentPrice());
        }

        for (String key : updateUser.keySet()) {
            bidMapper.updateUserGold(key, updateUser.get(key));
        }
    }

    public Bid readLastItemToBid() {
        return bidMapper.readLastItemToBid();
    }

    public List<Pair<Bid, Item>> searchItemsToBid(BidSearchFilter bid) {

        List<Bid> bidList = bidMapper.searchBidData(bid);
        List<Pair<Bid, Item>> pairs = new ArrayList<>();

        for (Bid bids : bidList) {
            Bid resultBid = bidItemDAO.readBidWithCache(bids.getId());
            Item resultItem = itemDAO.readItemWithCache(bids.getItemId());

            Pair<Bid, Item> pair = Pair.of(resultBid, resultItem);
            pairs.add(pair);
        }

        return pairs;
    }
}
