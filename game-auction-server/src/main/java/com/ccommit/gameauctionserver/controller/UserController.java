package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    // 로그인페이지 이동
    @GetMapping("/login")
    public String createUser()
    {
        return "redirect:/login";
    }

    //회원가입 정보 저장
    @PostMapping("/sign-up")
    public String login(@RequestBody User user){
        boolean saveResult = userService.checkUserID(user.getUser_id()) ;

        if(saveResult)
        {
            return "fail";

        }
        userService.createUser(user);
        return "login";

    }
}
