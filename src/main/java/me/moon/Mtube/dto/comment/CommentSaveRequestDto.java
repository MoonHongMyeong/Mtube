package me.moon.Mtube.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String content;
    private Long userId;
    private Long parent;
    private Long video_post_id;

    public void setPostId(Long postId){
        this.video_post_id=postId;
    }

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public void setParent(Long parent) {
        this.parent=parent;
    }

    @Builder
    public CommentSaveRequestDto(String content, Long userId, Long parent, Long video_post_id){
        this.parent=parent;
        this.userId=userId;
        this.video_post_id=video_post_id;
        this.content=content;
    }
}
