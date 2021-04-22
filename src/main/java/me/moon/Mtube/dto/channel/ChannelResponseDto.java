package me.moon.Mtube.dto.channel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelResponseDto {
    private Long id;
    private String name;
    private String description;
    private int subscribe_count;
    private String created_date;
    private Long user_id;
}
