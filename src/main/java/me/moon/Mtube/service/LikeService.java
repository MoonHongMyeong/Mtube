package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.like.LikeCommentResponseDto;
import me.moon.Mtube.dto.like.LikeCountResponseDto;
import me.moon.Mtube.dto.like.LikePostResponseDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.CommentMapper;
import me.moon.Mtube.mapper.LikeMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    /*
        포스트
    */

    public void likePost(String userEmail, Long postId) {
        Optional<LikePostResponseDto> likePostDto = toExistLikePost(userEmail, postId);
        if(likePostDto.isPresent()){
            if(likePostDto.get().getKind() == "DISLIKE"){
                likeMapper.updateLikePost(likePostDto.get().getUser_id(), likePostDto.get().getVideo_post_id());
            }else{
                throw new IllegalArgumentException("이미 좋아요를 했습니다.");
            }
        }else{
            Long userId = userMapper.findUserByEmail(userEmail).getId();
            likeMapper.likePost(userId, postId);
        }
    }

    private Optional<LikePostResponseDto> toExistLikePost(String userEmail, Long postId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        return likeMapper.toExistLikePost(userId, postId);
    }

    public void cancelLikePost(String userEmail, Long postId) {
        Optional<LikePostResponseDto> likePostDto = toExistLikePost(userEmail, postId);
        if(likePostDto.isPresent()){
            Long userId = likePostDto.get().getUser_id();
            likeMapper.cancelLikePost(userId, postId);
        }else{
            throw new UnsuitableUserException("좋아요를 표시한 채널이 아닙니다 \n 잘못 된 요청입니다.");
        }
    }

    public void dislikePost(String userEmail, Long postId) {
        Optional<LikePostResponseDto> likePostDto = toExistLikePost(userEmail, postId);
        if(!likePostDto.isPresent()){
            Long userId = userMapper.findUserByEmail(userEmail).getId();
            likeMapper.dislikePost(userId, postId);
        }else{
            if(likePostDto.get().getKind() == "DISLIKE"){
                throw new IllegalArgumentException("이미 싫어요 표시를 했습니다. 잘못 된 요청입니다.");
            }else{
                likeMapper.updateDislikePost(likePostDto.get().getUser_id(), likePostDto.get().getVideo_post_id());
            }
        }
    }

    public void cancelDislikePost(String userEmail, Long postId) {
        Optional<LikePostResponseDto> likePostDto = toExistLikePost(userEmail, postId);
        if(likePostDto.isPresent()){
            Long userId = likePostDto.get().getUser_id();
            likeMapper.cancelLikePost(userId, postId);
        }else{
            throw new UnsuitableUserException("싫어요를 표시한 채널이 아닙니다 \n 잘못 된 요청입니다.");
        }
    }

    public LikeCountResponseDto getCount(Long postId) {
        return likeMapper.getCount(postId);
    }

    /*
        댓글
     */

    public void likeComment(String userEmail, Long commentId) {
        Optional<LikeCommentResponseDto> likeCommentDto = toExistLikeComment(userEmail, commentId);
        if(likeCommentDto.isPresent()){
            if(likeCommentDto.get().getKind() == "DISLIKE"){
                likeMapper.updateLikeComment(likeCommentDto.get().getUser_id(), commentId);
            }else{
                throw new IllegalArgumentException("이미 좋아요를 했습니다.");
            }
        }else{
            Long userId = userMapper.findUserByEmail(userEmail).getId();
            plusLikeCount(commentId);
            likeMapper.likeComment(userId, commentId);
        }
    }

    private void plusLikeCount(Long commentId) {
        commentMapper.plusLikeCount(commentId);
    }

    private Optional<LikeCommentResponseDto> toExistLikeComment(String userEmail, Long commentId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        return likeMapper.toExistLikeComment(userId, commentId);
    }

    public void dislikeComment(String userEmail, Long commentId) {
        Optional<LikeCommentResponseDto> likeCommentDto = toExistLikeComment(userEmail, commentId);
        if(likeCommentDto.isPresent()){
            if(likeCommentDto.get().getKind() == "Like"){
                likeMapper.updateDislikeComment(likeCommentDto.get().getUser_id(), commentId);
            }else{
                throw new IllegalArgumentException("이미 싫어요를 했습니다.");
            }
        }else{
            Long userId = userMapper.findUserByEmail(userEmail).getId();
            plusDislikeCount(commentId);
            likeMapper.dislikeComment(userId, commentId);
        }
    }

    private void plusDislikeCount(Long commentId) {
        commentMapper.plusDislikeCount(commentId);
    }

    public void cancelLikeComment(String userEmail, Long commentId) {
        Optional<LikeCommentResponseDto> likeCommentDto = toExistLikeComment(userEmail, commentId);
        if(!likeCommentDto.isPresent()){
            throw new IllegalArgumentException("좋아요를 하지 않았습니다.");
        }else{
            Long userId=userMapper.findUserByEmail(userEmail).getId();
            minusLikeCount(commentId);
            likeMapper.cancelLikeComment(userId, commentId);
        }
    }

    private void minusLikeCount(Long commentId) {
        commentMapper.minusLikeCount(commentId);
    }

    public void cancelDislikeComment(String userEmail, Long commentId) {
        Optional<LikeCommentResponseDto> likeCommentDto = toExistLikeComment(userEmail, commentId);
        if(!likeCommentDto.isPresent()){
            throw new IllegalArgumentException("싫어요를 하지 않았습니다.");
        }else{
            Long userId=userMapper.findUserByEmail(userEmail).getId();
            minusDislikeCount(commentId);
            likeMapper.cancelDislikeComment(userId, commentId);
        }
    }

    private void minusDislikeCount(Long commentId) {
        commentMapper.minusDislikeCount(commentId);
    }
}
