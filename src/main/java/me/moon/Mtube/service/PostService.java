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

    public List<PostResponseDto> getExplorePostList(String category) {
        if(category != null) {
            return postMapper.getExplorePostList(category);
        }else{
            return postMapper.getMainExplorePostList();
        }
    }

    public List<PostResponseDto> getUserSubscribePostList(Long userId) {
        if(userId == null){
            throw new IllegalArgumentException("로그인이 필요한 서비스 입니다.");
        }
        return postMapper.getUserSubscribePostList(userId);
    }

    public PostResponseDto getPost(UserResponseDto userDto, Long postId) {
        postMapper.plusViewCount(postId);
        watchRecordStart(userDto, postId);
        return postMapper.getPost(postId);
    }

    private void watchRecordStart(UserResponseDto userDto, Long postId) {
        if(userDto != null){
            postMapper.watchRecordStart(userDto.getId(), postId);
        }
    }

    public void recordEnd(UserResponseDto userDto, Long postId) {
        if(userDto != null){
            postMapper.watchRecordEnd(userDto.getId(), postId);
        }
    }

    public List<PostResponseDto> getChannelPostList(Long channelId) {
        return postMapper.getChannelPostList(channelId);
    }

    public void addPost(UserResponseDto userDto, Long channelId, PostSaveRequestDto saveRequestDto) {
        if(!isMatchChannelByUserId(userDto.getId(), channelId)){
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

    public void addTempPost(UserResponseDto userDto, Long channelId, PostSaveRequestDto saveRequestDto) {
        if(!isMatchChannelByUserId(userDto.getId(), channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 포스트를 임시 등록할 수 있습니다.");
        }
        postMapper.addTempPost(saveRequestDto);
    }

    public void updatePost(UserResponseDto userDto, Long channelId, Long postId, PostUpdateRequestDto updateRequestDto) {
        if(!isMatchChannelByUserId(userDto.getId(), channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 작성한 포스트를 수정할 수 있습니다.");
        }
        postMapper.updatePost(updateRequestDto);
    }

    public void deletePost(UserResponseDto userDto, Long channelId, Long postId) {
        if(!isMatchChannelByUserId(userDto.getId(), channelId)){
            throw new UnsuitableUserException("본인의 채널이 아닙니다. \n 본인의 채널에서만 작성한 포스트를 삭제할 수 있습니다.");
        }
        postMapper.deletePost(postId);
    }


}
