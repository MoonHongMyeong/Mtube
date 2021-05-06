package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.dto.user.UserSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean toExistEmail(String email);

    void insertUser(UserSaveRequestDto userDto);

    UserResponseDto getUser(Long userId);

    void updateUser(Long userId, String name, String picture);

    boolean toExistUserById(Long userId);

    boolean validateUser(Long userId, String encryptPassword);

    void changePassword(Long userId, String newPassword);

    void deleteUser(Long userId);

    LoginUserDto findUserByEmail(String email);

    void addUserPlaylist(Long userId, String name);

    void updateUserPlaylistName(Long playlistId, String name);

    void deleteUserPlaylist(Long playlistId);
}
