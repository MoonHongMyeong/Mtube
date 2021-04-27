package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
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
            throw new IllegalArgumentException("이미 구독 중 입니다.");
        }
        subscribeMapper.subscribe(userId, channelId);
    }

    private boolean toExistSubscribe(Long userId, Long channelId) {
        return subscribeMapper.toExistSubscribe(userId, channelId);
    }

}
