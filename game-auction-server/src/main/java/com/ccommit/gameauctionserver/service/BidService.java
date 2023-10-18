package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dao.BidItemDAO;
import com.ccommit.gameauctionserver.dao.HistoryBidDAO;
import com.ccommit.gameauctionserver.dao.ItemDAO;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.BidWithUserDTO;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BidService {

    private BidMapper bidMapper;
    private ItemMapper itemMapper;
    private UserMapper userMapper;
    private BidItemDAO bidItemDAO;
    private HistoryBidDAO historyBidDAO;
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

        Bid bidItem = bidItemDAO.readBidWithCache(bidId);
        RequestUserInfo userInfo = userMapper.readUserInfo(userId);

        if (bidItem == null || bidItem.isSold()) {
            throw new CustomException(ErrorCode.ITEM_FORBIDDEN);
        } else if (bidItem.getSellerId().equals(userId) || userId.equals(bidItem.getHighestBidderId())) {
            throw new CustomException(ErrorCode.BID_AUTHORITY);
        } else if (bidItem.getPresentPrice() >= priceGold || bidItem.getPrice() < priceGold
                || userInfo.getGold() < priceGold) {
            throw new CustomException(ErrorCode.BID_CREDIT_CANCLED);
        }

        BidWithUserDTO bidWithUserDTO = BidWithUserDTO.builder()
                .bid(bidItem)
                .userInfo(userInfo)
                .pirceGold(priceGold)
                .build();
        try {
            bidMQProducer.ProduceBidData(bidWithUserDTO);
        } catch (Exception e) {
            if (e instanceof ConnectException) {
                historyBidDAO.insertHistoryUpdateBid(bidWithUserDTO);
                throw new CustomException(ErrorCode.SERVER_INTERNAL);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }


/*
        Bid responseBid = Bid.builder()
                .id(bidId)
                .createTime(bidItem.getCreateTime())
                .limitTime(bidItem.getLimitTime())
                .price(bidItem.getPrice())
                .startPrice(bidItem.getStartPrice())
                .presentPrice(priceGold)
                .highestBidderId(userId)
                .sellerId(bidItem.getSellerId())
                .isSold(priceGold==bidItem.getPrice())
                .itemId(bidItem.getItemId())
                .build();
*/

        bidItem.setPresentPrice(priceGold);
        bidItem.setHighestBidderId(userId);
        bidItem.setSold(priceGold == bidItem.getPrice());
        return bidItemDAO.UpdateCacheData(bidItem);
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
