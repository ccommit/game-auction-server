package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.BidService;
import com.ccommit.gameauctionserver.service.LoginService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    private final LoginService loginService;

    @PostMapping("/item")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> registerItemToBid(@RequestBody Bid bid) {
        String sellerId = loginService.getCurrentUser();
        bid.setSellerId(sellerId);

        bidService.registrationItem(bid);

        return ApiResponse.createSuccess(bidService.readLastItemToBid());
    }

    @GetMapping("/items")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> readUserItemsToBid()
    {
        String sellerId = loginService.getCurrentUser();

        List<Bid> bidList = bidService.readUserItemsToBid(sellerId);
        return ApiResponse.createSuccess(bidList);
    }
}
