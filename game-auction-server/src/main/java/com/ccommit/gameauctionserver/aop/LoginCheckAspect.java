package com.ccommit.gameauctionserver.aop;

import com.ccommit.gameauctionserver.annotation.CheckLoginStatus;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.exception.ErrorCode;
import com.ccommit.gameauctionserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
        switch (checkLogin.userType().toString()) {
            case "USER":
                userId = SessionUtil.getCurrentUserFromSession(session);
                break;
            case "ADMIN":
                userId = SessionUtil.getCurrentAdminUserFromSession(session);
                break;
            default:
                throw new CustomException(ErrorCode.USER_AUTHORIZATION);
        }
        if (userId == null) {
            throw new CustomException(ErrorCode.USER_AUTHORIZATION);
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if(proceedingJoinPoint.getArgs()!=null)
            modifiedArgs[index] = userId;
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}
