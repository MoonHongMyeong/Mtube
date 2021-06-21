package me.moon.Mtube.dto.playlist;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelPlaylistResponseDto {
    private Long id;
    private Long channelId;
    private String name;
}
