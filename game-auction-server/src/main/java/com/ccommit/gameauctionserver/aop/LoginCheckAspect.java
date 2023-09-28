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

    @Before("@annotation(com.ccommit.gameauctionserver.annotation.CheckLoginStatus) && @annotation(checkLogin)")
    public void loginCheck(CheckLoginStatus checkLogin) throws HttpClientErrorException
    {
        String UserId = loginService.getCurrentUser();

        /*
        TODO :
        5번이슈 (아이템 입찰)시 어뷰징유저의 경우 입찰 서비스를 이용하지 못하도록 구현 예정입니다.
        1. checkLogin.userType()
        2. RequestUserInfo userInfo = userService.readUserInfo(loginService.getUserId());
        3. if (checkLogin.userType() != userInfo.getUserType())
           throw new CustomException("해당 유저 ID에서는 권한이 없습니다."){};
        */
    }

    private String getCurrentUser() throws HttpClientErrorException
    {
        String userId = loginService.getCurrentUser();
        if(userId == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "ID가 없습니다.") {};
        }

        return userId;
    }
}
