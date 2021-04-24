package me.moon.Mtube.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long channelId;
    private String channelName;
    private String category;
    private String playlist;
    private String title;
    private String content;
    private String videoFile;
    private int viewCount;
    private String permitComment;
    private String createdDate;
}
