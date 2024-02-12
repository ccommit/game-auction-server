package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    boolean isExistId(String userId);
    void createUser(User user);
    Integer getID(String userId, String password);
    void updateUser(RequestUserInfo userInfo);
    RequestUserInfo readUserInfo(String userId);
}
