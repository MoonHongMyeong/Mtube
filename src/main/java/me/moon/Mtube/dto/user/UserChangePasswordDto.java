package me.moon.Mtube.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserChangePasswordDto {
    private String password;
    private String newPassword;
}