package me.moon.Mtube.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserChangePasswordDto {
    private String password;
    private String newPassword;

    @Builder
    public UserChangePasswordDto(String password, String newPassword){
        this.password=password;
        this.newPassword=newPassword;
    }
}