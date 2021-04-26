package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;

import java.util.List;

public interface CommentMapper {
    List<CommentResponseDto> getCommentList(Long postId);

    void addComment(CommentSaveRequestDto saveRequestDto);

    CommentResponseDto getComment(Long commentId);
}
