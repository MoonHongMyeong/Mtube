package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.service.SubscribeService;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;
    private final SessionLoginUser loginUser;

    @PostMapping(value = {"/api/v1/video/subscribe", "/api/v1/channel/{channelId}/subscribe"})
    public ResponseEntity subscribe(@RequestParam("channel_id") Long channelId){
        String userEmail = loginUser.getCurrentUser();
        subscribeService.subscribe(userEmail, channelId);
        return new ResponseEntity(new Message("subscribe success!"), HttpStatus.CREATED);
    }
}
