package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.mapper.TransactionHistoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionHistoryServiceTest {

    @Mock
    private TransactionHistoryMapper transactionHistoryMapper;

    @InjectMocks
    private TransactionHistoryService transactionHistoryService;

    @Test
    @DisplayName("검색한 아이템이름과 일치한 판매내역을 조회합니다.")
    public void loadTransactionHistoryDataByItemNameTest()
    {
        when(transactionHistoryMapper.readTransactionHistoryData(any(String.class),any(String.class)))
                .thenReturn(new ArrayList<>());

        transactionHistoryService.loadTransactionHistoryData("TestSeller","TestItemName");

        verify(transactionHistoryMapper).readTransactionHistoryData(any(String.class),any(String.class));
    }

    @Test
    @DisplayName("로그인한 유저의 판매 혹은 구매 내역을 조회합니다.")
    public void loadTransactionHistoryDataByUserIDTest()
    {
        when(transactionHistoryMapper.readTransactionHistoryData(any(String.class),isNull(String.class)))
                .thenReturn(new ArrayList<>());

        transactionHistoryService.loadTransactionHistoryData("TestSeller","");

        verify(transactionHistoryMapper).readTransactionHistoryData(any(String.class),isNull(String.class));
    }
}
