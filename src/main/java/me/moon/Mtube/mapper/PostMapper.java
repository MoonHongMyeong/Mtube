package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.dto.post.PostUpdateRequestDto;

import java.util.List;

public interface PostMapper {
    List<PostResponseDto> getPostList();

    List<PostResponseDto> getChannelPostList(Long channelId);

    void plusViewCount(Long postId);

    PostResponseDto getPost(Long postId);

    void addPost(PostSaveRequestDto saveRequestDto);

    void addTempPost(PostSaveRequestDto saveRequestDto);

    void updatePost(PostUpdateRequestDto updateRequestDto);

    void deletePost(Long id);

    void watchRecordStart(Long userId, Long postId);

    void watchRecordEnd(Long userId, Long postId);
}
