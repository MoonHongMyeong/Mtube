package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.like.LikePostResponseDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.LikeMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeMapper likeMapper;
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
}
