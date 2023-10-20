package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.TransactionHistoryDTO;
import com.ccommit.gameauctionserver.mapper.TransactionHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryService {

    @Autowired
    TransactionHistoryMapper transactionHistoryMapper;

    public List<TransactionHistoryDTO> loadTransactionHistoryByUserId(String userId)
    {
        List<TransactionHistoryDTO> transactionHistoryDTOList = new ArrayList<>();
        transactionHistoryDTOList = transactionHistoryMapper.readTransactionHistoryByUserId(userId);

        return transactionHistoryDTOList;
    }

    public List<TransactionHistoryDTO> loadTransactionHistoryByItemName(String itemName)
    {
        List<TransactionHistoryDTO> transactionHistoryDTOList = new ArrayList<>();
        transactionHistoryDTOList = transactionHistoryMapper.readTransactionHistoryByItemName(itemName);

        return transactionHistoryDTOList;
    }

}
