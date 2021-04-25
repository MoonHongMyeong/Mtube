package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.comment.CommentResponseDto;

import java.util.List;

public interface CommentMapper {
    List<CommentResponseDto> getComment(Long postId);
}
