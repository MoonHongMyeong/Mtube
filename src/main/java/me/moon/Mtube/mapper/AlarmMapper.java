package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.user.UserResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmMapper {
    boolean toExistAlarm(Long userId, Long channelId);

    void addAlarm(Long userId, Long channelId);

    List<UserResponseDto> getAlarmUser(Long channelId);
}
