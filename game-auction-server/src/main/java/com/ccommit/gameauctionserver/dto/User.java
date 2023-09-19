package com.ccommit.gameauctionserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int id;

    private String userID;
    private String password;
    private String nickname;
    private String phoneNumber;

    private int gold = 0;
    private int userLevel = 1;

    private boolean isAdmin = false;
    private boolean isAbusing = false;
}
