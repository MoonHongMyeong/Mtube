package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.UserSaveRequestDto;
import me.moon.Mtube.exception.DuplicatedEmailException;
import me.moon.Mtube.mapper.UserMapper;
import me.moon.Mtube.util.PasswordEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
