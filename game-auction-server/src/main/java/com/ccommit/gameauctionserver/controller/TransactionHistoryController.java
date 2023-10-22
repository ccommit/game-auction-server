package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.TransactionHistoryService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @PostMapping("")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> transactionHistoryData(String userId, @RequestParam("itemName") String itemName)
    {
        return ApiResponse.createSuccess(transactionHistoryService.loadTransactionHistoryData(userId, itemName));
    }

}
