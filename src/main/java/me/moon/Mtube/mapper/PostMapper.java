package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.post.PostSaveRequestDto;


public interface PostMapper {

    void addPost(PostSaveRequestDto saveRequestDto);

}
