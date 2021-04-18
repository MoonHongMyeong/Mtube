package me.moon.Mtube.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String name;
    private String email;
    private String password;
    private String picture;

    @Builder
    public UserSaveRequestDto(String name, String email, String password, String picture){
        this.name=name;
        this.email=email;
        this.password=password;
        this.picture=picture;
    }

    public void changeEncryptedPassword(String encryptedPassword){
        this.password=encryptedPassword;
    }
}
