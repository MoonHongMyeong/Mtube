package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.dto.post.PostUpdateRequestDto;
import me.moon.Mtube.service.PostService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final SessionLoginUser loginUser;

    //포스트 목록 조회
    @GetMapping("/api/v1/video")
    public List<PostResponseDto> getPostList(){
        return postService.getPostList();
    }
    //포스트 조회
    @GetMapping("/api/v1/video/{postId}")
    public PostResponseDto getPost(@PathVariable("postId") Long postId){
        String userEmail=loginUser.getCurrentUser();
        return postService.getPost(userEmail, postId);
    }

    @PutMapping("/api/v1/video/{postId}/recordEnd")
    public ResponseEntity recordEnd(@PathVariable("postId") Long postId){
        String userEmail=loginUser.getCurrentUser();
        postService.recordEnd(userEmail, postId);
        return new ResponseEntity(new Message("기록종료"), HttpStatus.OK);
    }
    //채널에 등록된 포스트 조회
    @GetMapping("/api/v1/channel/{channelId}/video")
    public List<PostResponseDto> getChannelPostList(@PathVariable("channelId") Long channelId){
        return postService.getChannelPostList(channelId);
    }
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
    //포스트 수정
    @PutMapping("/api/v1/channel/{channelId}/video/{postId}")
    public ResponseEntity updatePost(@PathVariable("channelId") Long channelId, @PathVariable("postId") Long postId, @RequestBody PostUpdateRequestDto updateRequestDto){
        String userEmail = loginUser.getCurrentUser();
        postService.updatePost(userEmail, channelId, postId, updateRequestDto);
        return new ResponseEntity(new Message("post update success!"),HttpStatus.OK);
    }
    //포스트 삭제
    @DeleteMapping("/api/v1/channel/{channelId}/video/{postId}")
    public ResponseEntity deletePost(@PathVariable("channelId") Long channelId, @PathVariable("postId")Long postId){
        String userEmail = loginUser.getCurrentUser();
        postService.deletePost(userEmail, channelId, postId);
        return new ResponseEntity(new Message("post delete success!"), HttpStatus.OK);
    }
}
