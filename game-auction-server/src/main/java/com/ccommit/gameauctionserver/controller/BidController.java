package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.dto.bid.BidSearchFilter;
import com.ccommit.gameauctionserver.dto.bid.ResponseItemToBid;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.BidService;
import com.ccommit.gameauctionserver.utils.SessionUtil;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    @PostMapping("/item")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> registerItemToBid(String userId, @RequestBody Bid bid) {

        bidService.registrationItem(bid, userId);

        return ApiResponse.createSuccess(bidService.readLastItemToBid());
    }

    @PostMapping("/search")
    public ApiResponse<?> searchItem(@RequestBody BidSearchFilter bid)
    {
        List<ResponseItemToBid> itemList = bidService.searchItemsToBid(bid);

        return ApiResponse.createSuccess(itemList);
    }

    @GetMapping("/search/{bidId}")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> showSelectItemInfo(String userId, @PathVariable("bidId") int bidId)
    {
        Bid bid = bidService.readItemWithBid(bidId);
        return ApiResponse.createSuccess(bid);
    }

    @PostMapping("/{productId}")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> bidSelectItem(String userId, @PathVariable("productId") int bidId,
                                    @RequestBody Map<String,Integer> param)
    {
        Bid bid = bidService.updateItemWithBid(bidId,userId, param.get("priceGold"));
        return ApiResponse.createSuccess(bid);
    }
}
