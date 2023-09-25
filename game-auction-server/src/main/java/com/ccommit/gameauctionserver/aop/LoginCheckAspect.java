package com.ccommit.gameauctionserver.aop;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.service.LoginService;
import com.ccommit.gameauctionserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final LoginService loginService;
    private final UserService userService;

    @Before("@annotation(com.ccommit.gameauctionserver.annotation.CheckLoginStatus)")
    public void loginCheck() throws HttpClientErrorException
    {
        userLoginCheck();
/*        switch (userType.userType()) {
            case USER -> {
                userLoginCheck();
            }
            case ABUSER -> {
                abuserLoginCheck();
            }
            case ADMIN -> {
                adminLoginCheck();
            }
        }*/
    }

    private String getCurrentUser() throws HttpClientErrorException
    {
        String userId = loginService.getCurrentUser();
        if(userId == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "ID가 없습니다.") {};
        }

        return userId;
    }

    private void userLoginCheck()
    {
        String userId = getCurrentUser();
    }

    private void abuserLoginCheck()
    {

    }

    private void adminLoginCheck()
    {

    }

}
