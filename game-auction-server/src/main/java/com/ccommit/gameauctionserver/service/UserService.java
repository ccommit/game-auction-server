package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean isExistId(String userId) {

        boolean existId = userMapper.isExistId(userId);
        if (existId) {
            throw new CustomException(ErrorCode.USER_DUPLICATED);
        } else {
            return existId;
        }
    }

    public void createUser(User user) {
        userMapper.createUser(user);
    }

    public boolean compareUserInfo(String userId, String password) {

        Integer id = userMapper.getID(userId,password);
        if(id == null)
        {
            throw new CustomException(ErrorCode.USER_FORBIDDEN);
        } else {
            return true;
        }
    }

    public RequestUserInfo findUserInfoByID(String userId) {
        return userMapper.readUserInfo(userId);
    }

    public void updateUserInfo(RequestUserInfo userInfo) {
        userMapper.updateUser(userInfo);
    }

}
