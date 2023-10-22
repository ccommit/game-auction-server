package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.SanctionHistoryDTO;
import com.ccommit.gameauctionserver.dto.bid.BidStatus;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.service.AdminService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/user/{userId}/{userType}")
    @CheckLoginStatus(userType = UserType.ADMIN)
    public ApiResponse<?> updateUserType(String adminId, @PathVariable("userId") String userId, @PathVariable UserType userType)
    {
        return ApiResponse.createSuccess(adminService.updateUserType(adminId, userId,userType));
    }

    @PatchMapping("/bid/{bidId}/{saleStatus}")
    @CheckLoginStatus(userType = UserType.ADMIN)
    public ApiResponse<?> insertSanctionHistory(String adminId, @PathVariable("bidId") int bidId, @PathVariable("saleStatus") BidStatus bidStatus)
    {
        return ApiResponse.createSuccess(adminService.updateBidStatus(adminId, bidId, bidStatus));
    }

}
