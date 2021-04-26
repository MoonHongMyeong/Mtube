package me.moon.Mtube.dto.comment;

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
}
