package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/video/{postId}/comment")
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    //댓글 리스트 조회
    @GetMapping
    public List<CommentResponseDto> getComment(@PathVariable("postId") Long postId){
        return commentService.getComment(postId);
    }
    //댓글 등록

    //댓글 수정

    //댓글 삭제

}
