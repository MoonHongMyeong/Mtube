package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.service.StudioService;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    스튜디오 기능
 */

@RequestMapping("/api/v1/channel/{channelId}")
@RequiredArgsConstructor
@RestController
public class StudioApiController {

    private final SessionLoginUser loginUser;
    private final StudioService studioService;

    //채널의 모든 댓글 조회
    @GetMapping("/comment")
    public List<CommentResponseDto> getStudioComment(@PathVariable("channelId") Long channelId){
        String userEmail = loginUser.getCurrentUser();
        return studioService.getStudioComment(userEmail,channelId);
    }

    //채널의 포스트에 달린 댓글에 하트 주기
    @PutMapping("/comment/{commentId}/heart")
    public ResponseEntity giveHeart(@PathVariable("channelId") Long channelId, @PathVariable("commentId") Long commentId){
        String userEmail= loginUser.getCurrentUser();
        studioService.giveHeart(userEmail, channelId, commentId);
        return new ResponseEntity(new Message("해당 댓글에 하트를 추가하였습니다"), HttpStatus.OK);
    }

    //채널의 포스트에 달린 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteCommentByPostOwner(@PathVariable("channelId") Long channelId, @PathVariable("commentId") Long commentId){
        String userEmail= loginUser.getCurrentUser();
        studioService.deleteCommentByPostOwner(userEmail, channelId, commentId);
        return new ResponseEntity(new Message("해당 댓글을 삭제하였습니다."), HttpStatus.OK);
    }
}
