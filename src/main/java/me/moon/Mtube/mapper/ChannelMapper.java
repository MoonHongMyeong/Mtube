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

    void deleteChannel(Long id);

    boolean toExistChannelByName(String name);

    void addChannelPlaylist(ChannelPlaylistSaveRequestDto saveRequestDto);

    void updateChannelPlaylist(ChannelPlaylistUpdateRequestDto updateRequestDto);

    void deleteChannelPlaylist(Long playlistId);

    Long getChannelIdByChannelName(String name);

    boolean isMatchChannelByUserId(Long userId, Long channelId);
}
