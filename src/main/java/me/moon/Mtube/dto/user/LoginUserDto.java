package me.moon.Mtube.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String picture;
}
