package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.dto.post.PostUpdateRequestDto;


public interface PostMapper {

    void addPost(PostSaveRequestDto saveRequestDto);

    void addTempPost(PostSaveRequestDto saveRequestDto);

    void updatePost(PostUpdateRequestDto updateRequestDto);

    void deletePost(Long id);

}
