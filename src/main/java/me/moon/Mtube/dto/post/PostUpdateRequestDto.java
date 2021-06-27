package me.moon.Mtube.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private Long id;
    private String category;
    private Long playlist;
    private String title;
    private String videoFile;
    private String content;

    @Builder
    public PostUpdateRequestDto(Long id, String category, Long playlist, String title, String videoFile, String content){
        this.id=id;
        this.category=category;
        this.playlist=playlist;
        this.title=title;
        this.videoFile=videoFile;
        this.content=content;
    }
}
