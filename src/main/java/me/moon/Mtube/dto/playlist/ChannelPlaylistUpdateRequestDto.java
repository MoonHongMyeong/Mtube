package me.moon.Mtube.dto.playlist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelPlaylistUpdateRequestDto {
    private Long id;
    private String name;

    public void setId(Long id){
        this.id=id;
    }

    @Builder
    public ChannelPlaylistUpdateRequestDto(Long id, String name){
        this.id=id;
        this.name=name;
    }
}
