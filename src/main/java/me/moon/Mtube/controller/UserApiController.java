package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.UserChangePasswordDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.dto.user.UserSaveRequestDto;
import me.moon.Mtube.dto.user.UserUpdateRequestDto;
import me.moon.Mtube.service.UserService;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    //회원가입
    @PostMapping
    public ResponseEntity signUp(@RequestBody UserSaveRequestDto saveRequestDto){
        userService.signUp(saveRequestDto);
        return new ResponseEntity(new Message("sign up success!"),HttpStatus.CREATED);
    }
    //회원 조회
    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable("userId") Long userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequestDto updateRequestDto){
        userService.updateUser(userId, updateRequestDto);
        return new ResponseEntity(new Message("update success!"), HttpStatus.OK);
    }

}
