package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.service.LikeService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/video/{postId}")
@RequiredArgsConstructor
@RestController
public class LikeApiController {

    private final LikeService likeService;
    private final SessionLoginUser loginUser;

    /*
        포스트
    */
    //포스트 좋아요
    @PostMapping("/like")
    public ResponseEntity likePost(@PathVariable("postId") Long postId){
        String userEmail = loginUser.getCurrentUser();
        likeService.likePost(userEmail, postId);
        return new ResponseEntity(new Message("post like success!"), HttpStatus.CREATED);
    }

}
