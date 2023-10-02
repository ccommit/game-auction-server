package com.ccommit.gameauctionserver.dto;

import com.ccommit.gameauctionserver.dto.user.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int id;

    private String userId;
    private String password;
    private String nickname;
    private String phoneNumber;

    private int gold = 0;
    private int userLevel = 1;

    private UserType userType;
}
