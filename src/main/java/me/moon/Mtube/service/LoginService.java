package me.moon.Mtube.service;


import me.moon.Mtube.dto.user.UserResponseDto;

public interface LoginService {
    void loginUser(UserResponseDto userDto);
    void logoutUser();
    UserResponseDto getCurrentUser();
}
