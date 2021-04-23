package me.moon.Mtube.dto.playlist;

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
}
