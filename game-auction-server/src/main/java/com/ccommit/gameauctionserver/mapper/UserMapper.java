package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    // ID 중복체크
    int checkUserID(String userID);

    // 회원가입
    int getMaxID();
    void createUser(User user);

    // 로그인
    Integer getID(String userID, String password);
    User readUser(int id);

    // 회원정보 수정
    void updateUser(User user);

    // 회원 탈퇴
    void deleteUser(User user);

    void deleteAll();
}
