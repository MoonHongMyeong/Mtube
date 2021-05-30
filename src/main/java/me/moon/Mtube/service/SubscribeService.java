package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.SubscribeMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeMapper subscribeMapper;
    private final UserMapper userMapper;

    public void subscribe(UserResponseDto userDto, Long channelId) {
        if(toExistSubscribe(userDto.getId(), channelId)){
            throw new UnsuitableUserException("이미 구독 중 입니다.");
        }
        subscribeMapper.subscribe(userDto.getId(), channelId);
    }

    private boolean toExistSubscribe(Long userId, Long channelId) {
        return subscribeMapper.toExistSubscribe(userId, channelId);
    }

    public void cancelSubscribe(UserResponseDto userDto, Long channelId) {
        if(!toExistSubscribe(userDto.getId(), channelId)){
            throw new UnsuitableUserException("구독 중이 아닙니다. \n 잘못 된 요청입니다.");
        }
        subscribeMapper.cancelSubscribe(userDto.getId(), channelId);
    }
}
