package com.ccommit.gameauctionserver.controller;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public String createUser()
    {
        return "redirect:/login";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody User user){
        boolean saveResult = false;
        saveResult = userService.checkUserID(user.getUserID()) ;

        if(saveResult)
        {
            return "Fail : UserID is Duplicate";
        }
        userService.createUser(user);
        return "Sign-up Success.";

    }

    @PostMapping("/login")
    public String login(String userID, String userPassword, HttpSession session)
    {
        boolean checkInfo = false;
        checkInfo = userService.compareUserInfo(userID, userPassword);

        if(!checkInfo)
        {
            return "Message,Fail to login";
        }

        User user = userService.findUserInfoByID(userService.getID(userID,userPassword));
        session.setAttribute("LoginUserInfo", user);
        return "successPage(redirect:/bid)";
    }

    @PostMapping("/logout")
    public void logOut(HttpSession session)
    {
        userService.logout(session);
    }

    //DB 비우기
    @PostMapping("/deleteAll")
    public String deleteAll()
    {
        userService.deleteAll();
        return "Delete";
    }

}
