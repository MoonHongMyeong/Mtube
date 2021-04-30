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
    //포스트 좋아요 취소
    @DeleteMapping("/like")
    public ResponseEntity cancelLikePost(@PathVariable("postId") Long postId){
        String userEmail = loginUser.getCurrentUser();
        likeService.cancelLikePost(userEmail, postId);
        return new ResponseEntity(new Message("post like cancel success!"), HttpStatus.OK);
    }
    //포스트 싫어요
    @PostMapping("/dislike")
    public ResponseEntity dislikePost(@PathVariable("postId") Long postId){
        String userEmail = loginUser.getCurrentUser();
        likeService.dislikePost(userEmail, postId);
        return new ResponseEntity(new Message("post dislike success!"), HttpStatus.CREATED);
    }
    //포스트 싫어요 취소
    @DeleteMapping("/dislike")
    public ResponseEntity cancelDislikePost(@PathVariable("postId") Long postId){
        String userEmail = loginUser.getCurrentUser();
        likeService.cancelDislikePost(userEmail, postId);
        return new ResponseEntity(new Message("post dislike cancel success!"), HttpStatus.OK);
    }

}
