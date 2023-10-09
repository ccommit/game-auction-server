package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.*;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/* TODO :
 2~4번 이슈가 추가적으로 수정이 필요해서 병합하기위해 BidController과 분리하였습니다.
 2~4번이슈 수정 완료후에 해당 컨트롤러를 BidController과 병합할 예정입니다.
 BidItem*.java,xml 파일들은 모두 같은 이유를 가지고 있습니다.*/
@RestController
@RequestMapping("/bidss")
@RequiredArgsConstructor
public class BidItemController {

    private final LoginService loginService;
    private final ItemService itemService;
    private final BidItemService bidItemService;
    private final UserService userService;

    //TODO : 입찰 기능 구현 (present)
    @PostMapping("/biditem/{id}")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> bidItem(@RequestBody String bidId, int pricePrice)
    {
        String buyerId = loginService.getCurrentUserFromSession();



        return ApiResponse.createSuccess(bidId);
    }

    @PostMapping("/instantitem")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> instantBidToItem(@RequestBody String bidId)
    {
        String buyerId = loginService.getCurrentUserFromSession();
        bidItemService.instantBuyItem(bidId, buyerId);

        RequestUserInfo user = userService.findUserInfoByID(buyerId);
        // List<Object> add user,item
        return ApiResponse.createSuccess(user);
    }

}
