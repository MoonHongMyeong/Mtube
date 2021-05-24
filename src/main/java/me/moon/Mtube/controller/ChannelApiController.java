package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.comment.ChannelCommentResponseDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistUpdateRequestDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.service.ChannelService;
import me.moon.Mtube.service.CommentService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/channel")
@RestController
@RequiredArgsConstructor
public class ChannelApiController {

    private final ChannelService channelService;
    private final CommentService commentService;
    private final SessionLoginUser loginUser;

    //채널 조회
    @GetMapping("/{channelId}")
    public ChannelResponseDto getChannel(@PathVariable("channelId") Long channelId){
        return channelService.getChannel(channelId);
    }
    //채널 추가
    @PostMapping
    public ResponseEntity addChannel(@RequestBody ChannelSaveRequestDto saveRequestDto){
        UserResponseDto userDto = loginUser.getCurrentUser();
        channelService.addChannel(userDto, saveRequestDto);
        return new ResponseEntity(new Message("channel make success!"), HttpStatus.CREATED);
    }

    //채널 수정
    @PutMapping("/{channelId}")
    public ResponseEntity updateChannel(@PathVariable("channelId") Long channelId, @RequestBody ChannelUpdateRequestDto updateRequestDto){
        UserResponseDto userDto = loginUser.getCurrentUser();
        channelService.updateChannel(userDto, channelId, updateRequestDto);
        return new ResponseEntity(new Message("channel update success!"), HttpStatus.OK);
    }

    //채널 삭제
    @DeleteMapping("/{channelId}")
    public ResponseEntity deleteChannel(@PathVariable("channelId") Long channelId){
        UserResponseDto userDto = loginUser.getCurrentUser();
        channelService.deleteChannel(userDto, channelId);
        return new ResponseEntity(new Message("channel delete success!"), HttpStatus.OK);
    }

    //채널 플레이리스트 생성
    @PostMapping("/{channelId}/playlist")
    public ResponseEntity addChannelPlaylist(@PathVariable("channelId") Long channelId, @RequestBody ChannelPlaylistSaveRequestDto saveRequestDto){
        channelService.addChannelPlaylist(channelId,saveRequestDto);
        return new ResponseEntity(new Message("playlist create success!"), HttpStatus.CREATED);
    }
    //채널 플레이리스트 수정
    @PutMapping("/{channelId}/playlist/{playlistId}")
    public ResponseEntity updateChannelPlaylist(@PathVariable("playlistId") Long playlistId, @RequestBody ChannelPlaylistUpdateRequestDto updateRequestDto){
        channelService.updateChannelPlaylist(playlistId, updateRequestDto);
        return new ResponseEntity(new Message("playlist update success!"), HttpStatus.OK);
    }
    //채널 플레이리스트 삭제
    @DeleteMapping("/{channelId}/playlist/{playlistId}")
    public ResponseEntity deleteChannelPlaylist(@PathVariable("playlistId") Long playlistId){
        channelService.deleteChannelPlaylist(playlistId);
        return new ResponseEntity(new Message("playlist delete success!"), HttpStatus.OK);
    }

    //채널관리자의 댓글 조회
    @GetMapping("/{channelId}/comment")
    public List<ChannelCommentResponseDto> videoOwnerGetCommentList(@PathVariable("channelId") Long channelId){
        return channelService.videoOwnerGetCommentList(channelId);
    }

    //채널관리자의 댓글 삭제
    @DeleteMapping("/{channelId}/comment/{commentId}")
    public ResponseEntity videoOwnerDeleteComment(@PathVariable("channelId") Long channelId, @PathVariable("commentId") Long commentId){
        UserResponseDto userDto = loginUser.getCurrentUser();
        channelService.deleteCommentByVideoOwner(channelId, commentId, userDto);
        return new ResponseEntity(new Message("댓글을 삭제했습니다."), HttpStatus.OK);
    }
}
