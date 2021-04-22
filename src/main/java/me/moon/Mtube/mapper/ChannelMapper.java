package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistUpdateRequestDto;

public interface ChannelMapper {
    ChannelResponseDto getChannel(Long id);

    void addChannel(ChannelSaveRequestDto saveRequestDto);

    void updateChannel(ChannelUpdateRequestDto updateRequestDto);

    boolean toExistChannelByName(String name);
}
