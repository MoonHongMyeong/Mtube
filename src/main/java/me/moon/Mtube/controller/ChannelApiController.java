package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistUpdateRequestDto;
import me.moon.Mtube.service.ChannelService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/channel")
@RestController
@RequiredArgsConstructor
public class ChannelApiController {

    private final ChannelService channelService;
    private final SessionLoginUser loginUser;

    //채널 조회
    @GetMapping("/{channelId}")
    public ChannelResponseDto getChannel(@PathVariable("channelId") Long channelId){
        return channelService.getChannel(channelId);
    }
    //채널 추가
    @PostMapping
    public ResponseEntity addChannel(@RequestBody ChannelSaveRequestDto saveRequestDto){
        String userEmail = loginUser.getCurrentUser();
        channelService.addChannel(userEmail, saveRequestDto);
        return new ResponseEntity(new Message("channel make success!"), HttpStatus.CREATED);
    }

}