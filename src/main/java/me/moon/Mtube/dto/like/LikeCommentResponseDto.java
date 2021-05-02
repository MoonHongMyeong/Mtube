package me.moon.Mtube.dto.like;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeCommentResponseDto {
    private Long user_id;
    private Long comment_id;
    private String kind;
    private String created_date;
}
