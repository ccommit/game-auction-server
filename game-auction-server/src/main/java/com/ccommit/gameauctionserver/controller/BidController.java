package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.service.BidService;
import com.ccommit.gameauctionserver.service.ItemService;
import com.ccommit.gameauctionserver.service.LoginService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
@EnableScheduling
public class BidController {

    private final ItemService itemService;
    private final BidService bidService;
    private final LoginService loginService;

    @PostMapping("/registrationitem")
    @CheckLoginStatus(userType = CheckLoginStatus.UserType.USER)
    public ApiResponse<?> registerItemToBid(@RequestBody Bid bid)
    {
        String sellerID = loginService.getCurrentUser();
        bid.setSellerID(sellerID);
        bidService.registrationItem(bid);

        return ApiResponse.createSuccess(bid);

    }


    @Scheduled(cron = "0 0/1 * * * *")
    public void checkLimitTime()
    {
        bidService.deleteItemToBid();
    }
}
