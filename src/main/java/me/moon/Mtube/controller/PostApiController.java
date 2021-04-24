package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.service.PostService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final SessionLoginUser loginUser;

    //포스트 등록
    @PostMapping("/api/v1/channel/{channelId}/video")
    public ResponseEntity addPost(@PathVariable("channelId") Long channelId, @RequestBody PostSaveRequestDto saveRequestDto){
        String userEmail = loginUser.getCurrentUser();
        postService.addPost(userEmail, channelId, saveRequestDto);
        return new ResponseEntity(new Message("post add success!"), HttpStatus.CREATED);
    }
    //임시 포스트 등록
    @PostMapping("/api/v1/channel/{channelId}/video/temp")
    public ResponseEntity addTempPost(@PathVariable("channelId") Long channelId ,@RequestBody PostSaveRequestDto saveRequestDto){
        String userEmail = loginUser.getCurrentUser();
        postService.addTempPost(userEmail, channelId, saveRequestDto);
        return new ResponseEntity(new Message("temp post add success"), HttpStatus.CREATED);
    }
}
