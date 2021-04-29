package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.SubscribeMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeMapper subscribeMapper;
    private final UserMapper userMapper;

    public void subscribe(String userEmail, Long channelId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        if(toExistSubscribe(userId, channelId)){
            throw new UnsuitableUserException("이미 구독 중 입니다.");
        }
        subscribeMapper.subscribe(userId, channelId);
    }

    private boolean toExistSubscribe(Long userId, Long channelId) {
        return subscribeMapper.toExistSubscribe(userId, channelId);
    }

    public void cancelSubscribe(String userEmail, Long channelId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        if(!toExistSubscribe(userId, channelId)){
            throw new UnsuitableUserException("구독 중이 아닙니다. \n 잘못 된 요청입니다.");
        }
        subscribeMapper.cancelSubscribe(userId, channelId);
    }
}
