package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.service.StudioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
