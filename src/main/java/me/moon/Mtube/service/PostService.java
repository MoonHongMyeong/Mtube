package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.mail.MailDto;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.dto.post.PostUpdateRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.AlarmMapper;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.PostMapper;
import me.moon.Mtube.mapper.UserMapper;
import me.moon.Mtube.util.MailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final UserMapper userMapper;
    private final ChannelMapper channelMapper;
    private final PostMapper postMapper;
    private final AlarmMapper alarmMapper;

    public List<PostResponseDto> getPostList() {
        return postMapper.getPostList();
    }

    public PostResponseDto getPost(String userEmail, Long postId) {
        postMapper.plusViewCount(postId);
        watchRecordStart(userEmail, postId);
        return postMapper.getPost(postId);
    }

    private void watchRecordStart(String userEmail, Long postId) {
        if(userEmail != null){
            Long userId = userMapper.findUserByEmail(userEmail).getId();
            postMapper.watchRecordStart(userId, postId);
        }
    }

    public void recordEnd(String userEmail, Long postId) {
        if(userEmail != null){
            Long userId = userMapper.findUserByEmail(userEmail).getId();
            postMapper.watchRecordEnd(userId, postId);
        }
    }

    public List<PostResponseDto> getChannelPostList(Long channelId) {
        return postMapper.getChannelPostList(channelId);
    }

    public void addPost(String userEmail, Long channelId, PostSaveRequestDto saveRequestDto) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        Long userId = userDto.getId();
        if(!isMatchChannelByUserId(userId, channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 포스트를 등록할 수 있습니다.");
        }
        postMapper.addPost(saveRequestDto);
        //비디오 등록 후 실시간 알림
        List<UserResponseDto> alarmUserList = alarmMapper.getAlarmUser(channelId);
        ChannelResponseDto channelDto = channelMapper.getChannel(channelId);
        alarmUserList
                .stream()
                .forEach((user)->
                        new MailSender().sendMail(MailDto
                                .builder()
                                .address(user.getEmail())
                                .title(saveRequestDto.getTitle())
                                .message(channelDto.getName()+"님이 새로운 비디오를 업로드 했습니다.")
                                .build()));
    }

    private boolean isMatchChannelByUserId(Long userId, Long channelId) {
        return channelMapper.isMatchChannelByUserId(userId, channelId);
    }

    public void addTempPost(String userEmail, Long channelId, PostSaveRequestDto saveRequestDto) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        Long userId = userDto.getId();
        if(!isMatchChannelByUserId(userId, channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 포스트를 임시 등록할 수 있습니다.");
        }
        postMapper.addTempPost(saveRequestDto);
    }

    public void updatePost(String userEmail, Long channelId, Long postId, PostUpdateRequestDto updateRequestDto) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        Long userId = userDto.getId();
        if(!isMatchChannelByUserId(userId, channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 작성한 포스트를 수정할 수 있습니다.");
        }
        postMapper.updatePost(updateRequestDto);
    }

    public void deletePost(String userEmail, Long channelId, Long postId) {
        LoginUserDto userDto = userMapper.findUserByEmail(userEmail);
        Long userId = userDto.getId();
        if(!isMatchChannelByUserId(userId, channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 작성한 포스트를 삭제할 수 있습니다.");
        }
        postMapper.deletePost(postId);
    }



}
