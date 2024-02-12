package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.utils.SessionUtil;
import com.ccommit.gameauctionserver.service.UserService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<?> signUp(@RequestBody User user) {
        boolean isExist = userService.isExistId(user.getUserId());

        if(!isExist) {
            userService.createUser(user);
        }
        return ApiResponse.createSuccess(user);
    }

    @PostMapping("/login")
    public ApiResponse<?> login(HttpSession httpSession,@RequestBody User user) {
        boolean checkInfo = false;
        checkInfo = userService.compareUserInfo(user.getUserId(), user.getPassword());

        if (checkInfo) {
            SessionUtil.loginUser(httpSession, user.getUserId(), userService.findUserInfoByID(user.getUserId()));
        }
        return ApiResponse.createSuccess(SessionUtil.getCurrentUserFromSession(httpSession));
    }

    @PostMapping("/logout")
    @CheckLoginStatus(userType = UserType.USER)
    public void logout(String userId, HttpSession session) {
        SessionUtil.logoutUser(session);
    }

    @GetMapping("/mypage")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> mypage(String userId) {
        RequestUserInfo userInfo = userService.findUserInfoByID(userId);
        return ApiResponse.createSuccess(userInfo);
    }

    @PostMapping("/mypage/update")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> updateUser(String userId, @RequestBody RequestUserInfo userInfo) {
        userInfo.setUserId(userId);
        userService.updateUserInfo(userInfo);

        userInfo = userService.findUserInfoByID(userId);
        return ApiResponse.createSuccess(userInfo);
    }
}
