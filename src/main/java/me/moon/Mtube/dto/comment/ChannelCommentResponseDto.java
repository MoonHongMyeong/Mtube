package me.moon.Mtube.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelCommentResponseDto {
    private Long user_id;
    private String content;
    private int like_count;
    private int dislike_count;
    private String heart;
    private Long parent;
    private Long video_post_id;
    private String modified_date;
}
