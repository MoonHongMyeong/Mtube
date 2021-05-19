package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.comment.CommentResponseDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.CommentMapper;
import me.moon.Mtube.mapper.StudioMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudioService {

    private final UserMapper userMapper;
    private final ChannelMapper channelMapper;
    private final StudioMapper studioMapper;
    private final CommentMapper commentMapper;

    public List<CommentResponseDto> getStudioComment(String userEmail, Long channelId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        ChannelResponseDto myChannel = channelMapper.getChannel(channelId);
        if(userId != myChannel.getUser_id()){
            throw new UnsuitableUserException("채널의 주인만 사용할 수 있는 기능입니다.");
        }
        return studioMapper.getStudioComment(channelId);
    }

    public void giveHeart(String userEmail, Long channelId, Long commentId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        Long channelUserId=channelMapper.getChannel(channelId).getUser_id();
        if(userId!=channelUserId){
            throw new UnsuitableUserException("채널의 관리자만 사용할 수 있는 기능입니다.");
        }
        studioMapper.giveHeart(commentId);
    }

    public void deleteCommentByPostOwner(String userEmail, Long channelId, Long commentId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        Long channelUserId=channelMapper.getChannel(channelId).getUser_id();
        if(userId!=channelUserId){
            throw new UnsuitableUserException("채널의 관리자만 사용할 수 있는 기능입니다.");
        }
        commentMapper.deleteComment(commentId);
    }

    public int getTotalView(String userEmail, Long channelId) {
        Long userId = userMapper.findUserByEmail(userEmail).getId();
        Long channelUserId=channelMapper.getChannel(channelId).getUser_id();
        if(userId != channelUserId){
            throw new UnsuitableUserException("채널의 관리자만 사용할 수 있는 기능입니다.");
        }
        return studioMapper.getTotalView(channelId);
    }

    public int getSubscriberCount(Long channelId) {
        return studioMapper.getSubscriberCount(channelId);
    }
}
