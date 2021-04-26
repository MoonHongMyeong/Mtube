package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.CommentMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public List<CommentResponseDto> getCommentList(Long postId) {
        return commentMapper.getCommentList(postId);
    }

    public void addComment(String userEmail, Long postId, CommentSaveRequestDto saveRequestDto) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        saveRequestDto.setUserId(userDto.getId());
        saveRequestDto.setPostId(postId);
        commentMapper.addComment(saveRequestDto);
    }

    public void addReplies(String userEmail, Long parent, Long postId, CommentSaveRequestDto saveRequestDto) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        saveRequestDto.setUserId(userDto.getId());
        saveRequestDto.setPostId(postId);
        saveRequestDto.setParent(parent);
        commentMapper.addComment(saveRequestDto);
    }

}
