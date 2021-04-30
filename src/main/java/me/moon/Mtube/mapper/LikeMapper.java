package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.like.LikePostResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface LikeMapper {
    /*
        포스트
    */

    Optional<LikePostResponseDto> toExistLikePost(Long userId, Long postId);

    void likePost(Long userId, Long postId);

    void updateLikePost(Long userId, Long postId);

    void cancelLikePost(Long userId, Long postId);
}
