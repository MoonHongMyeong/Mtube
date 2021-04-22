package me.moon.Mtube.dto.channel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelSaveRequestDto {
    private String name;
    private String description;
    private Long user_id;

    public void setUser(Long user_id){
        this.user_id=user_id;
    }
}
