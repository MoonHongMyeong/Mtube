package me.moon.Mtube.dto.post;

import lombok.Builder;
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

    @Builder
    public PostSaveRequestDto(Long channelId, String category, Long playlist, String title, String videoFile, String content, String permitComment, String temp){
        this.channelId=channelId;
        this.category=category;
        this.playlist=playlist;
        this.title=title;
        this.videoFile=videoFile;
        this.content=content;
        this.permitComment=permitComment;
        this.temp=temp;
    }
}
