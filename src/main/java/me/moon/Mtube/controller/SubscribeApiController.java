package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.service.SubscribeService;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;
    private final SessionLoginUser loginUser;

    @PostMapping("/api/v1/channel/{channelId}/subscribe")
    public ResponseEntity subscribe(@RequestParam("channel_id") Long channelId){
        UserResponseDto userDto = loginUser.getCurrentUser();
        subscribeService.subscribe(userDto, channelId);
        return new ResponseEntity(new Message("subscribe success!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1/channel/{channelId}/subscribe")
    public ResponseEntity cancelSubscribe(@RequestParam("channel_id") Long channelId){
        UserResponseDto userDto = loginUser.getCurrentUser();
        subscribeService.cancelSubscribe(userDto, channelId);
        return new ResponseEntity(new Message("cancel subscription success!"), HttpStatus.OK);
    }
}
