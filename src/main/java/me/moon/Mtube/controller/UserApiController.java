package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.like.UserLikePostResponseDto;
import me.moon.Mtube.dto.playlist.UserPlaylistResponseDto;
import me.moon.Mtube.dto.user.*;
import me.moon.Mtube.service.LoginService;
import me.moon.Mtube.service.UserService;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final LoginService loginService;

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

    @PutMapping("/{userId}/password")
    public ResponseEntity changePassword(@PathVariable("userId") Long userId, @RequestBody UserChangePasswordDto changePasswordDto){
        userService.changePassword(userId, changePasswordDto);
        return new ResponseEntity(new Message("change password success!"), HttpStatus.OK);
    }

    @DeleteMapping("/userId")
    public ResponseEntity withdrawal(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity(new Message("withdrawal success!"), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginUserDto userDto){
        Optional<LoginUserDto> user = userService.findUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        if(user.isPresent()){
            loginService.loginUser(user.get().getEmail());
            return new ResponseEntity(new Message("Login Success!"), HttpStatus.OK);
        }else{
            return new ResponseEntity(new Message("Login failed..."),HttpStatus.NOT_FOUND);
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout(){
        loginService.logoutUser();
        return new ResponseEntity(new Message("logout success!"), HttpStatus.OK);
    }


    /*
        재생목록 기능
     */
    //리스트 조회
    @GetMapping("/{userId}/playlist/{playlistId}")
    public List<UserPlaylistResponseDto> getPlaylist(@PathVariable("userId")Long userId, @PathVariable("playlistId") Long playlistId){
        return userService.getPlaylist(userId, playlistId);
    }
    //리스트 등록
    @PostMapping("/{userId}/playlist")
    public ResponseEntity addUserPlaylist(@PathVariable("userId")Long userId, @RequestParam("name") String name){
        userService.addUserPlaylist(userId, name);
        return new ResponseEntity(new Message("add playlist success!"), HttpStatus.CREATED);
    }
    //리스트 이름 수정
    @PutMapping("/{userId}/playlist/{playlistId}")
    public ResponseEntity updateUserPlaylistName(@PathVariable("userId")Long userId,
                                                 @PathVariable("playlistId") Long playlistId,
                                                 @RequestParam("name") String name){
        String userEmail = loginService.getCurrentUser();
        userService.updateUserPlaylistName(userEmail ,userId, playlistId, name);
        return new ResponseEntity(new Message("update playlist name success!"), HttpStatus.OK);
    }
    //리스트 삭제
    @DeleteMapping("/{userId}/playlist/{playlistId}")
    public ResponseEntity deleteUserPlaylist(@PathVariable("userId") Long userId, @PathVariable("playlistId") Long playlistId){
        String userEmail = loginService.getCurrentUser();
        userService.deleteUserPlaylist(userEmail, userId, playlistId);
        return new ResponseEntity(new Message("delete playlist success!"), HttpStatus.OK);
    }
    //유저의 플레이리스트 복사
    @PostMapping("/{userId}/playlist/{playlistId}/copy")
    public ResponseEntity copyUserPlaylist(@PathVariable("userId") Long userId, @PathVariable("playlistId")Long playlistId){
        String userEmail = loginService.getCurrentUser();
        userService.copyUserPlaylist(userEmail, userId, playlistId);
        return new ResponseEntity(new Message("coply playlist success!"), HttpStatus.CREATED);
    }
    //리스트에 포스트 추가
    @PostMapping("/{userId}/playlist/{playlistId}/{postId}")
    public ResponseEntity addPostInUserPlaylist(@PathVariable("userId") Long userId,
                                                @PathVariable("playlistId") Long playlistId,
                                                @PathVariable("postId") Long postId){
        String userEmail = loginService.getCurrentUser();
        userService.addPostInUserPlaylist(userEmail, userId, playlistId, postId);
        return new ResponseEntity(new Message("add video in your playlist success!"), HttpStatus.CREATED);
    }
    //리스트에 포스트 삭제
    @DeleteMapping("/{userId}/playlist/{playlistId}/{postId}")
    public ResponseEntity deletePostInUserPlaylist(@PathVariable("userId") Long userId,
                                                @PathVariable("playlistId") Long playlistId,
                                                @PathVariable("postId") Long postId){
        String userEmail = loginService.getCurrentUser();
        userService.deletePostInUserPlaylist(userEmail, userId, playlistId, postId);
        return new ResponseEntity(new Message("delete video in your playlist success!"), HttpStatus.OK);
    }

    //유저가 좋아요 한 리스트 조회
    @GetMapping("/{userId}/like")
    public List<UserLikePostResponseDto> getUserLikeList(@PathVariable("userId") Long userId){
        return userService.getUserLikeList(userId);
    }
}
