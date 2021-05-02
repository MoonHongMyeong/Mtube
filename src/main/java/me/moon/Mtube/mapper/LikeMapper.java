package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.like.LikeCommentResponseDto;
import me.moon.Mtube.dto.like.LikeCountResponseDto;
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

    void dislikePost(Long userId, Long postId);

    void updateDislikePost(Long userId, Long postId);

    LikeCountResponseDto getCount(Long postId);

    Optional<LikeCommentResponseDto> toExistLikeComment(Long userId, Long commentId);

    void likeComment(Long userId, Long commentId);

    void updateLikeComment(Long userId, Long commentId);

    void dislikeComment(Long userId, Long commentId);

    void updateDislikeComment(Long userId, Long commentId);
}
