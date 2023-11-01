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

    public List<TransactionHistoryDTO> loadTransactionHistoryData(String userId, String itemName)
    {
        if(itemName.equals(""))
        {
            itemName = null;
        }

        List<TransactionHistoryDTO> transactionHistoryDTOList = new ArrayList<>();
        transactionHistoryDTOList = transactionHistoryMapper.readTransactionHistoryData(userId,itemName);

        return transactionHistoryDTOList;
    }

}
