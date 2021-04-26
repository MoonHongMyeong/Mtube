package me.moon.Mtube.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String content;
    private int like_count;
    private int dislike_count;
    private String heart;
    private Long parent;
    private Long user_id;
}
