package com.ccommit.gameauctionserver.utils;

import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private static final String USER_ID = "USER_ID";
    private static final String USER_INFO = "USER_INFO";
    private static final String ADMIN_ID = "ADMIN_ID";

    public static void loginUser(HttpSession httpSession, String id, RequestUserInfo userInfo) {
        httpSession.setAttribute(USER_ID, id);
        httpSession.setAttribute(USER_INFO, userInfo);
    }

    public static void updateUserInfo(HttpSession httpSession, RequestUserInfo userInfo)
    {
        if(httpSession.getAttribute(USER_INFO) != null)
        {
            httpSession.removeAttribute(USER_INFO);
            httpSession.setAttribute(USER_INFO, userInfo);
        }
    }

    public static void loginAdmin(HttpSession httpSession, String id) {
        httpSession.setAttribute(ADMIN_ID, id);
    }

    public static void logoutUser(HttpSession httpSession) {
        httpSession.removeAttribute(USER_ID);
        httpSession.removeAttribute(USER_INFO);
    }

    public static String getCurrentUserFromSession(HttpSession httpSession) {
        return (String) httpSession.getAttribute(USER_ID);
    }

    public static RequestUserInfo getCurrentUserInfoFromSession(HttpSession httpSession){
        return (RequestUserInfo) httpSession.getAttribute(USER_INFO);
    }

    public static String getCurrentAdminUserFromSession(HttpSession httpSession) {
        return (String) httpSession.getAttribute(ADMIN_ID);
    }
}
