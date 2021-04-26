package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;
import me.moon.Mtube.service.CommentService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/video/{postId}/comment")
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;
    private final SessionLoginUser sessionLoginUser;

    //댓글 리스트 조회
    @GetMapping
    public List<CommentResponseDto> getComment(@PathVariable("postId") Long postId){
        return commentService.getComment(postId);
    }
    //댓글 등록
    @PostMapping
    public ResponseEntity addComment(@PathVariable("postId") Long postId, @RequestBody CommentSaveRequestDto saveRequestDto){
        String userEmail = sessionLoginUser.getCurrentUser();
        commentService.addComment(userEmail ,postId, saveRequestDto);
        return new ResponseEntity(new Message("comment add success!"), HttpStatus.CREATED);
    }
    @PostMapping("/{commentId}")
    public ResponseEntity addReplies(@PathVariable("commentId") Long parent, @PathVariable("postId") Long postId, @RequestBody CommentSaveRequestDto saveRequestDto){
        String userEmail = sessionLoginUser.getCurrentUser();
        commentService.addReplies(userEmail, parent, postId, saveRequestDto);
        return new ResponseEntity(new Message("comment add success!"), HttpStatus.CREATED);
    }
}
