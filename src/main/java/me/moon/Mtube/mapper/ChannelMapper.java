package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;

public interface ChannelMapper {
    ChannelResponseDto getChannel(Long id);

    void addChannel(ChannelSaveRequestDto saveRequestDto);

    void updateChannel(ChannelUpdateRequestDto updateRequestDto);

    void deleteChannel(Long id);

    boolean toExistChannelByName(String name);

    void addChannelPlaylist(ChannelPlaylistSaveRequestDto saveRequestDto);

    Long getChannelIdByChannelName(String name);
}
