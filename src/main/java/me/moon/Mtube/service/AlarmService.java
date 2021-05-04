package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.exception.DuplicatedAlarmException;
import me.moon.Mtube.mapper.AlarmMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmMapper alarmMapper;
    private final UserMapper userMapper;


    public void addAlarm(String userEmail, Long channelId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        if(toExistAlarm(userId, channelId)){
            throw new DuplicatedAlarmException("이미 실시간 알림등록을 했습니다.");
        }
        alarmMapper.addAlarm(userId, channelId);
    }

    private boolean toExistAlarm(Long userId, Long channelId) {
        return alarmMapper.toExistAlarm(userId, channelId);
    }


}
