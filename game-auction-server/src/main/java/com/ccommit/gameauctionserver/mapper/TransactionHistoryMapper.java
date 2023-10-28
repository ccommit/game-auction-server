package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.TransactionHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionHistoryMapper {

    public void insertTransactionHistory(List<Bid> transactionHistoryList);

    public List<TransactionHistoryDTO> readTransactionHistoryByUserId(String userId);

    public List<TransactionHistoryDTO> readTransactionHistoryByItemName(String itemName);

    public List<TransactionHistoryDTO> readTransactionHistoryData(String userId, String itemName);
}
