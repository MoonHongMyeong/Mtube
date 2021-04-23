package me.moon.Mtube.dto.playlist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelPlaylistSaveRequestDto {
    private Long channelId;
    private String name;

    public void setChannelId(Long channelId){
        this.channelId=channelId;
    }

    @Builder
    public ChannelPlaylistSaveRequestDto(Long channelId, String name){
        this.channelId=channelId;
        this.name=name;
    }
}
