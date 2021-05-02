package me.moon.Mtube.dto.like;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikePostResponseDto {
    private Long user_id;
    private Long video_post_id;
    private String kind;
    private String created_date;
}
