package com.ccommit.gameauctionserver.mapper;

import com.ccommit.gameauctionserver.dto.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    // ID 중복체크
    int hasUserID(String user_id);

    // 회원가입
    void createUser(User user);

    // 회원정보 수정
    void updateUser(User user);

    // 로그인
    void readUser(User user);

    // 로그아웃
    void logoutUser(User user);

    // 회원 탈퇴
    void deleteUser(User user);
}
