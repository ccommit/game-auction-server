package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.ResponseItemToBid;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.exception.DuplicateUserException;
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

        if(bidService.isExistItemId(bid.getItemId())) {
            throw new DuplicateUserException(bid.getItemId() + "는 이미 등록되어 있습니다.");
        }

        String sellerId = loginService.getCurrentUser();
        bid.setSellerId(sellerId);
        bidService.registrationItem(bid);

        return ApiResponse.createSuccess(bidService.readLastItemToBid());
    }


    @PostMapping("/search")
    public ApiResponse<?> searchItem(@RequestBody BidSearchFilter bid)
    {
        List<ResponseItemToBid> itemList = bidService.searchItemsToBid(bid);

        return ApiResponse.createSuccess(itemList);
    }
}
