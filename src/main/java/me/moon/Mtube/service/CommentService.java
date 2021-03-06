package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
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

    public void addComment(UserResponseDto userDto, Long postId, CommentSaveRequestDto saveRequestDto) {
        saveRequestDto.setUserId(userDto.getId());
        saveRequestDto.setPostId(postId);
        commentMapper.addComment(saveRequestDto);
    }

    public void addReplies(UserResponseDto userDto, Long parent, Long postId, CommentSaveRequestDto saveRequestDto) {
        saveRequestDto.setUserId(userDto.getId());
        saveRequestDto.setPostId(postId);
        saveRequestDto.setParent(parent);
        commentMapper.addComment(saveRequestDto);
    }

    public void updateComment(UserResponseDto userDto, Long commentId, String content) {
        if(isMatchCommentByUserId(userDto.getId(), commentId)){
            throw new UnsuitableUserException("본인이 작성한 댓글만 수정이 가능합니다.");
        }
        commentMapper.updateComment(commentId, content);
    }

    private boolean isMatchCommentByUserId(Long commentId, Long userId) {
        CommentResponseDto commentDto = commentMapper.getComment(commentId);
        if(commentDto.getIsRemoved() == "DELETED"){
            throw new IllegalArgumentException("이미 삭제 된 댓글 입니다.");
        }
        if(commentDto.getUser_id() != userId){
            return true;
        }else{
            return false;
        }
    }

    public void deleteComment(UserResponseDto userDto, Long commentId) {
        if(isMatchCommentByUserId(userDto.getId(), commentId)){
            throw new UnsuitableUserException("본인이 작성한 댓글만 삭제가 가능합니다.");
        }
        commentMapper.deleteComment(commentId);
    }
}
