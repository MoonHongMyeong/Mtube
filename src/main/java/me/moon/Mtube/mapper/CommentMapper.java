package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.comment.ChannelCommentResponseDto;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;

import java.util.List;

public interface CommentMapper {
    List<CommentResponseDto> getCommentList(Long postId);

    void addComment(CommentSaveRequestDto saveRequestDto);

    CommentResponseDto getComment(Long commentId);

    void updateComment(Long commentId, String content);

    void deleteComment(Long commentId);

    List<ChannelCommentResponseDto> videoOwnerGetCommentList(Long channelId);

    void plusLikeCount(Long commentId);

    void plusDislikeCount(Long commentId);

    void minusLikeCount(Long commentId);

    void minusDislikeCount(Long commentId);
}
