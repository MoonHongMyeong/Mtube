package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.*;
import me.moon.Mtube.exception.DuplicatedEmailException;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.UserMapper;
import me.moon.Mtube.util.PasswordEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(toExistUserById(userId)){
            throw new IllegalArgumentException("해당 계정이 존재하지 않습니다.");
        }
        userMapper.updateUser(userId, updateRequestDto.getName(), updateRequestDto.getPicture());
    }

    private boolean toExistUserById(Long userId) {
        return userMapper.toExistUserById(userId);
    }

    @Transactional
    public void changePassword(Long userId, UserChangePasswordDto changePasswordDto) {
        if(!validateUser(userId, changePasswordDto)){
            throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다. \n 다시 입력해주세요.");
        }
        userMapper.changePassword(userId, changePasswordDto.getNewPassword());
    }

    private boolean validateUser(Long userId, UserChangePasswordDto changePasswordDto) {
        String encryptPassword = encryptUser(changePasswordDto.getPassword());
        return userMapper.validateUser(userId, encryptPassword);
    }

    public void deleteUser(Long userId) {
        if(toExistUserById(userId)){
            throw new IllegalArgumentException("해당 계정이 존재하지 않습니다.");
        }
        userMapper.deleteUser(userId);
    }

    public Optional<LoginUserDto> findUserByEmailAndPassword(String email, String password) {
        Optional<LoginUserDto> user = Optional.ofNullable(userMapper.findUserByEmail(email));
        if(!user.isPresent()){
            throw new IllegalArgumentException("해당 이메일이 존재하지 않습니다. \n 다시 입력해주세요.");
        }
        if(!PasswordEncryptor.isMatch(password,user.get().getPassword())){
            throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다.");
        }
        return user;
    }

    public void addUserPlaylist(Long userId, String name) {
        userMapper.addUserPlaylist(userId, name);
    }

    public void updateUserPlaylistName(String userEmail, Long userId, Long playlistId, String name) {
        Long loginUserId = userMapper.findUserByEmail(userEmail).getId();
        if(loginUserId != userId){
            throw new UnsuitableUserException("자신의 플레이리스트만 수정할 수 있습니다.");
        }
        userMapper.updateUserPlaylistName(playlistId, name);
    }

    public void deleteUserPlaylist(String userEmail, Long userId, Long playlistId) {
        Long loginUserId = userMapper.findUserByEmail(userEmail).getId();
        if(loginUserId != userId){
            throw new UnsuitableUserException("자신의 플레이리스트만 삭제 가능합니다.");
        }
        userMapper.deleteUserPlaylist(playlistId);
    }
    @Transactional
    public void addPostInUserPlaylist(String userEmail, Long userId, Long playlistId, Long postId) {
        Long loginUserId = userMapper.findUserByEmail(userEmail).getId();
        if(loginUserId != userId){
            throw new UnsuitableUserException("자신의 플레이리스트에만 비디오를 등록할 수 있습니다.");
        }
        userMapper.addPostInUserPlaylist(postId, playlistId);
    }
}
