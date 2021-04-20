package me.moon.Mtube.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String picture;
}
