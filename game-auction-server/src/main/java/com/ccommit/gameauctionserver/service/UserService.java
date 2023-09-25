package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createUser(User user)
    {
        userMapper.createUser(user);
    }

    public boolean checkUserID(String username)
    {
        return userMapper.checkUserID(username) != 0;
    }

    public boolean compareUserInfo(String userID, String password)
    {
        return userMapper.getID(userID,password) != null;
    }
    public int getID(String userID, String password)
    {
       return userMapper.getID(userID,password);
    }
    public RequestUserInfo findUserInfoByID(String userId)
    {
        return userMapper.readUserInfo(userId);
    }

    public void updateUserInfo(RequestUserInfo userInfo)
    {
        userMapper.updateUser(userInfo);
    }

}
