package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.UserSaveRequestDto;
import me.moon.Mtube.service.UserService;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
