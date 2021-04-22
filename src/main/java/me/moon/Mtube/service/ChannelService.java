package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistUpdateRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChannelService {

    private final ChannelMapper channelMapper;
    private final UserMapper userMapper;

    public ChannelResponseDto getChannel(Long channelId) {
        return channelMapper.getChannel(channelId);
    }

    public void addChannel(String userEmail, ChannelSaveRequestDto saveRequestDto) {
        if(toExistChannelByName(saveRequestDto.getName())){
            throw new IllegalArgumentException("같은 이름의 채널이 존재합니다. \n 채널이름은 중복 될 수 없습니다.");
        }
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        saveRequestDto.setUser(userDto.getId());
        channelMapper.addChannel(saveRequestDto);
    }

    private boolean toExistChannelByName(String name) {
        return channelMapper.toExistChannelByName(name);
    }


}
