package me.moon.Mtube.service;



public interface LoginService {
    void loginUser(String email);
    void logoutUser();
    String getCurrentUser();
}
