package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // 회원 가입
    public void createUser(User user)
    {
        int maxID = userMapper.getMaxID();
        int pkID = (maxID != 0) ? maxID + 1 : 1;
        user.setId(pkID);
        userMapper.createUser(user);
    }

    //아이디 중복 확인
    public boolean checkUserID(String username)
    {
        return userMapper.checkUserID(username) != 0;
    }

    //로그인
    public boolean compareUserInfo(String userID, String password)
    {
        return userMapper.getID(userID,password) != null;
    }
    public int getID(String userID, String password)
    {
       return userMapper.getID(userID,password);
    }
    public User findUserInfoByID(int id)
    {
        return userMapper.readUser(id);
    }

    //로그아웃
    public void logout(HttpSession session)
    {
        session.invalidate();
    }

    //TODO: 회원 정보 수정

    //TODO: 회원 탈퇴

    public void deleteAll()
    {
        userMapper.deleteAll();
    }
}
