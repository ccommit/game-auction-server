package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.TransactionHistoryService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @GetMapping("")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> mytrade(String userId)
    {
        return ApiResponse.createSuccess(transactionHistoryService.loadTransactionHistoryByUserId(userId));
    }

    @GetMapping("/{itemName}")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> transactionHistoryByItemName(String userId, @PathVariable("itemName") String itemName)
    {


        return ApiResponse.createSuccess(transactionHistoryService.loadTransactionHistoryByItemName(itemName));
    }

}
