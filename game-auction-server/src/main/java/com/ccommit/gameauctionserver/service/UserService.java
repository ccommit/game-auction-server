package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.User;

import com.ccommit.gameauctionserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // 회원 가입
    public void createUser(User user)
    {
        userMapper.createUser(user);
    }
    // ID 중복 체크
    public boolean checkUserID(String username)
    {
        return userMapper.hasUserID(username) != 0;
    }
    //TODO: 로그인

    //TODO: 로그아웃

    //TODO: 회원 정보 수정

    //TODO: 회원 탈퇴
}
