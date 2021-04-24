package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.PostMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PostService {

    private final UserMapper userMapper;
    private final ChannelMapper channelMapper;
    private final PostMapper postMapper;

    public void addPost(String userEmail, Long channelId, PostSaveRequestDto saveRequestDto) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        Long userId = userDto.getId();
        if(!isMatchChannelByUserId(userId, channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 포스트를 등록할 수 있습니다.");
        }
        postMapper.addPost(saveRequestDto);
    }

    private boolean isMatchChannelByUserId(Long userId, Long channelId) {
        return channelMapper.isMatchChannelByUserId(userId, channelId);
    }
}
