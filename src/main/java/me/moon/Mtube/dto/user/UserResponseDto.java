package me.moon.Mtube.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;

    @Builder
    public UserResponseDto(LoginUserDto loginUserDto){
        this.id=loginUserDto.getId();
        this.name=loginUserDto.getName();
        this.email=loginUserDto.getEmail();
        this.picture=loginUserDto.getPicture();
    }
}
