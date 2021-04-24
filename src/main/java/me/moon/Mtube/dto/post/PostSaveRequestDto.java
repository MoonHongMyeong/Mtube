package me.moon.Mtube.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private Long channelId;
    private String category;
    private Long playlist;
    private String title;
    private String videoFile;
    private String content;
    private String permitComment;
    private String temp;
}
