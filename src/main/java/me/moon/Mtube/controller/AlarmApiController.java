package me.moon.Mtube.controller;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.service.AlarmService;
import me.moon.Mtube.service.SessionLoginUser;
import me.moon.Mtube.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/channel/{channelId}")
@RequiredArgsConstructor
@RestController
public class AlarmApiController {

    private final AlarmService alarmService;
    private final SessionLoginUser loginUser;

    @PostMapping("/alarm")
    public ResponseEntity addAlarm(@RequestParam("channel_id") Long channelId){
        String userEmail = loginUser.getCurrentUser();
        alarmService.addAlarm(userEmail, channelId);
        return new ResponseEntity(new Message("channel alarm add success"), HttpStatus.CREATED);
    }
}
