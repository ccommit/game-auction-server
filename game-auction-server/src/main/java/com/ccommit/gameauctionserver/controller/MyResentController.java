package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.MyResentTradeService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mytrade")
public class MyResentController {

    private final MyResentTradeService myResentTradeService;

    @GetMapping("")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> mytrade(String userId)
    {
        return ApiResponse.createSuccess(myResentTradeService.loadMyResentTrade(userId));
    }

}
