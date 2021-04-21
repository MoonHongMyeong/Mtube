package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginUser implements LoginService{

    private static final String USER_EMAIL = "USER_EMAIL";
    private final HttpSession httpSession;

    @Override
    public void loginUser(String email) {
        httpSession.setAttribute(USER_EMAIL, email);
    }

    @Override
    public void logoutUser() {
        httpSession.removeAttribute(USER_EMAIL);
    }

    @Override
    public String getCurrentUser() {
        return (String) httpSession.getAttribute(USER_EMAIL);
    }
}
