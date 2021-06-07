package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.like.UserLikePostResponseDto;
import me.moon.Mtube.dto.playlist.UserPlaylistResponseDto;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.user.*;
import me.moon.Mtube.exception.DuplicatedEmailException;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.UserMapper;
import me.moon.Mtube.util.PasswordEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public void signUp(UserSaveRequestDto saveRequestDto) {
        if (toExistEmail(saveRequestDto.getEmail())) {
            throw new DuplicatedEmailException("이메일이 중복 됩니다.");
        }
        String encryptedPassword = encryptUser(saveRequestDto.getPassword());
        saveRequestDto.changeEncryptedPassword(encryptedPassword);
        userMapper.insertUser(saveRequestDto);
        Long signUpUserId = userMapper.findUserByEmail(saveRequestDto.getEmail()).getId();
        userMapper.addUserPlaylist(signUpUserId, "나중에 볼 동영상");
    }

    private String encryptUser(String password) {
        return PasswordEncryptor.encrypt(password);
    }

    private boolean toExistEmail(String email) {
        return userMapper.toExistEmail(email);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) {
        return userMapper.getUser(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto updateRequestDto) {
        if (!toExistUserById(userId)) {
            throw new IllegalArgumentException("해당 계정이 존재하지 않습니다.");
        }
        userMapper.updateUser(userId, updateRequestDto.getName(), updateRequestDto.getPicture());
    }

    private boolean toExistUserById(Long userId) {
        return userMapper.toExistUserById(userId);
    }

    @Transactional
    public void changePassword(UserResponseDto userDto, UserChangePasswordDto changePasswordDto) {
        if(userDto.getEmail()==null){
            throw new IllegalArgumentException("로그인이 필요한 서비스 입니다.");
        }
        LoginUserDto loginUserDto = userMapper.findUserByEmail(userDto.getEmail());
        if (!PasswordEncryptor.isMatch(changePasswordDto.getPassword(), loginUserDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다. \n 다시 입력해주세요.");
        }
        userMapper.changePassword(loginUserDto.getId(), encryptUser(changePasswordDto.getNewPassword()));
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!toExistUserById(userId)) {
            throw new IllegalArgumentException("해당 계정이 존재하지 않습니다.");
        }
        userMapper.deleteUser(userId);
    }

    public Optional<LoginUserDto> findUserByEmailAndPassword(String email, String password) {
        Optional<LoginUserDto> user = Optional.ofNullable(userMapper.findUserByEmail(email));
        if (!user.isPresent()) {
            throw new IllegalArgumentException("해당 이메일이 존재하지 않습니다. \n 다시 입력해주세요.");
        }
        if (!PasswordEncryptor.isMatch(password, user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다.");
        }
        return user;
    }

    public void addUserPlaylist(Long userId, String name) {
        userMapper.addUserPlaylist(userId, name);
    }

    public void updateUserPlaylistName(UserResponseDto userDto, Long userId, Long playlistId, String name) {
        if (userDto.getId() != userId) {
            throw new UnsuitableUserException("자신의 플레이리스트만 수정할 수 있습니다.");
        }
        userMapper.updateUserPlaylistName(playlistId, name);
    }

    public void deleteUserPlaylist(UserResponseDto userDto, Long userId, Long playlistId) {
        if (userDto.getId() != userId) {
            throw new UnsuitableUserException("자신의 플레이리스트만 삭제 가능합니다.");
        }
        userMapper.deleteUserPlaylist(playlistId);
    }

    @Transactional
    public void addPostInUserPlaylist(UserResponseDto userDto, Long userId, Long playlistId, Long postId) {
        if (userDto.getId() != userId) {
            throw new UnsuitableUserException("자신의 플레이리스트에만 비디오를 등록할 수 있습니다.");
        }
        userMapper.addPostInUserPlaylist(postId, playlistId);
    }

    @Transactional
    public void deletePostInUserPlaylist(UserResponseDto userDto, Long userId, Long playlistId, Long postId) {
        if (userDto.getId() != userId) {
            throw new UnsuitableUserException("자신의 플레이리스트의 비디오만 삭제 가능합니다.");
        }
        userMapper.deletePostInUserPlaylist(postId, playlistId);
    }

    public void copyUserPlaylist(UserResponseDto userDto, Long userId, Long playlistId) {
        String playlistName = userMapper.getPlaylistName(playlistId);
        userMapper.addUserPlaylist(userDto.getId(), playlistName + "의 복사본");
        List<Long> newPlaylistId = userMapper.getUserPlaylist(userId);
        List<PostResponseDto> playlist = userMapper.getPlaylistInPostId(userId, playlistId);
        playlist.stream()
                .forEach((post) -> userMapper.addPostInUserPlaylist(post.getId(), newPlaylistId.get(newPlaylistId.size()) - 1));
    }

    public List<UserPlaylistResponseDto> getPlaylist(Long userId, Long playlistId) {
        return userMapper.getPlaylist(userId, playlistId);
    }

    public List<UserLikePostResponseDto> getUserLikeList(Long userId) {
        return userMapper.getUserLikeList(userId);
    }
}
