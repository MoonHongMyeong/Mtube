package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.comment.CommentResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudioMapper {
    List<CommentResponseDto> getStudioComment(Long channelId);

    void giveHeart(Long commentId);

    int getTotalView(Long channelId);
}
