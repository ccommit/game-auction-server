package com.ccommit.gameauctionserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String user_id;
    private String password;
    private String nickname;
    private String phoneNumber;

    private int gold = 0;
    private int user_level = 1;

    private boolean isAdmin = false;
    private boolean isAbusing = false;

    private boolean isLogin;
}
