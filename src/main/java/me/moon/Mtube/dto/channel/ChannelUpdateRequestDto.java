package me.moon.Mtube.dto.channel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelUpdateRequestDto {
    private Long id;
    private String description;

    public void setId(Long id){
        this.id=id;
    }
}
