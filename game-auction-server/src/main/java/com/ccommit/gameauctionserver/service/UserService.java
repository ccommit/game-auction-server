package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean isExistId(String userId) {
        return userMapper.isExistId(userId);
    }

    public void createUser(User user) {
        userMapper.createUser(user);
    }

    public boolean compareUserInfo(String userId, String password) {
        return userMapper.getID(userId, password) != null;
    }

    public RequestUserInfo findUserInfoByID(String userId) {
        return userMapper.readUserInfo(userId);
    }

    public void updateUserInfo(RequestUserInfo userInfo) {
        userMapper.updateUser(userInfo);
    }

}
