package me.moon.Mtube.dto.like;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeCountResponseDto {
    private int likeCount;
    private int dislikeCount;
}
