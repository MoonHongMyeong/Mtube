package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.like.UserLikePostResponseDto;
import me.moon.Mtube.dto.playlist.UserPlaylistResponseDto;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.dto.user.UserSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    void addPostInUserPlaylist(Long postId, Long playlistId);

    void deletePostInUserPlaylist(Long postId, Long playlistId);

    String getPlaylistName(Long playlistId);

    List<Long> getUserPlaylist(Long userId);

    List<UserPlaylistResponseDto> getUserPlaylists(Long userId);

    List<PostResponseDto> getPlaylistInPostId(Long userId, Long playlistId);

    List<UserPlaylistResponseDto> getPlaylist(Long userId, Long playlistId);

    List<UserLikePostResponseDto> getUserLikeList(Long userId);
}
