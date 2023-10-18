package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.service.LoginService;
import com.ccommit.gameauctionserver.service.UserService;
import com.ccommit.gameauctionserver.utils.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/sign-up")
    public ApiResponse<?> signUp(@RequestBody User user) {
        boolean isExist = userService.isExistId(user.getUserId());

        if (isExist) {
            throw new CustomException(ErrorCode.USER_DUPLICATED);
        }

        userService.createUser(user);
        return ApiResponse.createSuccess(user);
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody User user) {
        boolean checkInfo = false;
        checkInfo = userService.compareUserInfo(user.getUserId(), user.getPassword());

        if (!checkInfo) {
            return ApiResponse.createError("Wrong : ID or Password");
        } else {
            loginService.loginUser(user.getUserId());

            return ApiResponse.createSuccess(loginService.getCurrentUserFromSession());
        }
    }

    @PostMapping("/logout")
    @CheckLoginStatus(userType = UserType.USER)
    public void logout(HttpSession session) {
        loginService.logoutUser();
    }

    @GetMapping("/mypage")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> mypage() {
        RequestUserInfo userInfo = userService.findUserInfoByID(loginService.getCurrentUserFromSession());
        return ApiResponse.createSuccess(userInfo);
    }

    @PostMapping("/mypage/update")
    @CheckLoginStatus(userType = UserType.USER)
    public ApiResponse<?> updateUser(@RequestBody RequestUserInfo userInfo) {
        userInfo.setUserId(loginService.getCurrentUserFromSession());
        userService.updateUserInfo(userInfo);

        return ApiResponse.createSuccess(userInfo);
    }
}
