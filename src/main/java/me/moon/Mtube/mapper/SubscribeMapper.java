package me.moon.Mtube.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {
    boolean toExistSubscribe(Long userId, Long channelId);

    void subscribe(Long userId, Long channelId);
}
