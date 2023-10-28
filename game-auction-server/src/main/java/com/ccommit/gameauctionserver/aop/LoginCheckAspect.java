package com.ccommit.gameauctionserver.aop;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {
    @Around("@annotation(com.ccommit.gameauctionserver.annotation.CheckLoginStatus) && @annotation(checkLogin)")
    public Object loginCheck(ProceedingJoinPoint proceedingJoinPoint, CheckLoginStatus checkLogin) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        String userId = "";
        int index = 0;

        RequestUserInfo userInfo = SessionUtil.getCurrentUserInfoFromSession(session);
        if(userInfo == null){
            throw new CustomException(ErrorCode.USER_AUTHORIZATION);
        }

        if(checkLogin.userType() == userInfo.getUserType()) {
            userId = userInfo.getUserId();
        } else {
            throw new CustomException(ErrorCode.USER_AUTHORITY);
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if(proceedingJoinPoint.getArgs()!=null)
            modifiedArgs[index] = userId;
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}
