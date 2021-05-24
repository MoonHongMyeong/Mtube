package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginUser implements LoginService{

    private static final String USER = "USER";
    private final HttpSession httpSession;

    @Override
    public void loginUser(UserResponseDto userDto) {
        httpSession.setAttribute(USER, userDto);
    }

    @Override
    public void logoutUser() {
        httpSession.removeAttribute(USER);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        return (UserResponseDto) httpSession.getAttribute(USER);
    }
}
