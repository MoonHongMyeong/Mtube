package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;
import me.moon.Mtube.dto.user.UserResponseDto;
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
        return commentService.getCommentList(postId);
    }
    //댓글 등록
    @PostMapping
    public ResponseEntity addComment(@PathVariable("postId") Long postId, @RequestBody CommentSaveRequestDto saveRequestDto){
        UserResponseDto userDto = sessionLoginUser.getCurrentUser();
        commentService.addComment(userDto ,postId, saveRequestDto);
        return new ResponseEntity(new Message("comment add success!"), HttpStatus.CREATED);
    }
    //대댓글 등록
    @PostMapping("/{commentId}")
    public ResponseEntity addReplies(@PathVariable("commentId") Long parent, @PathVariable("postId") Long postId, @RequestBody CommentSaveRequestDto saveRequestDto){
        UserResponseDto userDto = sessionLoginUser.getCurrentUser();
        commentService.addReplies(userDto, parent, postId, saveRequestDto);
        return new ResponseEntity(new Message("comment add success!"), HttpStatus.CREATED);
    }
    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable("commentId") Long commentId, @RequestParam("content") String content){
        UserResponseDto userDto = sessionLoginUser.getCurrentUser();
        commentService.updateComment(userDto, commentId, content);
        return new ResponseEntity(new Message("comment update success!"), HttpStatus.OK);
    }
    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId){
        UserResponseDto userDto = sessionLoginUser.getCurrentUser();
        commentService.deleteComment(userDto, commentId);
        return new ResponseEntity(new Message("comment delete success!"), HttpStatus.OK);
    }
}
