package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    int checkUserID(String userID);
    void createUser(User user);
    Integer getID(String userID, String password);
    void updateUser(RequestUserInfo userInfo);
    void deleteUser(User user);
    RequestUserInfo readUserInfo(String userId);
}
