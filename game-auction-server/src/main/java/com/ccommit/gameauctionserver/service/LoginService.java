package com.ccommit.gameauctionserver.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final String USER_ID = "USER_ID";
    private final HttpSession session;

    public void loginUser(String id) {
        session.setAttribute(USER_ID, id);
    }

    public void logoutUser() {
        session.removeAttribute(USER_ID);
    }

    public String getCurrentUserFromSession() {
        return (String) session.getAttribute(USER_ID);
    }
}
