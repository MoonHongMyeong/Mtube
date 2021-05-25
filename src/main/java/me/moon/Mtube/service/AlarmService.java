package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.exception.DuplicatedAlarmException;
import me.moon.Mtube.mapper.AlarmMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmMapper alarmMapper;
    private final UserMapper userMapper;


    public void addAlarm(UserResponseDto userDto, Long channelId) {
        if(toExistAlarm(userDto.getId(), channelId)){
            throw new DuplicatedAlarmException("이미 실시간 알림등록을 했습니다.");
        }
        alarmMapper.addAlarm(userDto.getId(), channelId);
    }

    private boolean toExistAlarm(Long userId, Long channelId) {
        return alarmMapper.toExistAlarm(userId, channelId);
    }

    public void removeAlarm(UserResponseDto userDto, Long channelId) {
        if(!toExistAlarm(userDto.getId(), channelId)){
            throw new DuplicatedAlarmException("실시간 알람 등록을 하지 않았습니다.");
        }
        alarmMapper.removeAlarm(userDto.getId(), channelId);
    }
}
