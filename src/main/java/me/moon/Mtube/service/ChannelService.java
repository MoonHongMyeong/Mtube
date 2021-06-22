package me.moon.Mtube.service;

import lombok.RequiredArgsConstructor;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.comment.ChannelCommentResponseDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistResponseDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistUpdateRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.exception.UnsuitableUserException;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.CommentMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChannelService {

    private final ChannelMapper channelMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public ChannelResponseDto getChannel(Long channelId) {
        return channelMapper.getChannel(channelId);
    }

    public void addChannel(UserResponseDto userDto, ChannelSaveRequestDto saveRequestDto) {
        if(toExistChannelByName(saveRequestDto.getName())){
            throw new IllegalArgumentException("같은 이름의 채널이 존재합니다. \n 채널이름은 중복 될 수 없습니다.");
        }
        saveRequestDto.setUser(userDto.getId());
        channelMapper.addChannel(saveRequestDto);

        //채널 생성 시 '일반' 플레이리스트 추가
        Long channelId = channelMapper.getChannelIdByChannelName(saveRequestDto.getName());
        ChannelPlaylistSaveRequestDto playlistSaveRequestDto = ChannelPlaylistSaveRequestDto.builder()
                .channelId(channelId)
                .name("일반")
                .build();
        channelMapper.addChannelPlaylist(playlistSaveRequestDto);
    }

    private boolean toExistChannelByName(String name) {
        return channelMapper.toExistChannelByName(name);
    }

    public void updateChannel(UserResponseDto userDto, Long channelId, ChannelUpdateRequestDto updateRequestDto) {
        ChannelResponseDto channelDto = channelMapper.getChannel(channelId);
        if(channelDto.getUser_id() != userDto.getId()){
            throw new UnsuitableUserException("자신이 등록한 채널만 수정이 가능합니다. \n 잘못 된 요청입니다.");
        }
        updateRequestDto.setId(channelId);
        channelMapper.updateChannel(updateRequestDto);
    }

    public void deleteChannel(UserResponseDto userDto, Long channelId) {
        ChannelResponseDto channelDto = channelMapper.getChannel(channelId);
        if(channelDto.getUser_id() != userDto.getId()){
            throw new UnsuitableUserException("자신이 등록한 채널만 삭제가 가능합니다. \n 잘못 된 요청입니다.");
        }
        channelMapper.deleteChannel(channelId);
    }

    /*
    채널 플레이리스트
    */
    public List<ChannelPlaylistResponseDto> getChannelPlaylist(Long channelId) {
        return channelMapper.getChannelPlaylist(channelId);
    }
    public void addChannelPlaylist(Long channelId, ChannelPlaylistSaveRequestDto saveRequestDto) {
        saveRequestDto.setChannelId(channelId);
        channelMapper.addChannelPlaylist(saveRequestDto);
    }

    public void updateChannelPlaylist(Long playlistId, ChannelPlaylistUpdateRequestDto updateRequestDto) {
        updateRequestDto.setId(playlistId);
        channelMapper.updateChannelPlaylist(updateRequestDto);
    }

    public void deleteChannelPlaylist(Long playlistId) {
        channelMapper.deleteChannelPlaylist(playlistId);
    }

    /*
    채널 댓글 관리 기능
    */
    
    public void deleteCommentByVideoOwner(Long channelId, Long commentId, UserResponseDto userDto) {
        ChannelResponseDto channelDto = channelMapper.getChannel(channelId);
        if(userDto.getId() != channelDto.getUser_id()){
            throw new UnsuitableUserException("본인이 작성한 포스트의 댓글만 삭제 가능합니다.");
        }
        commentMapper.deleteComment(commentId);
    }

    public List<ChannelCommentResponseDto> videoOwnerGetCommentList(Long channelId) {
        return commentMapper.videoOwnerGetCommentList(channelId);
    }


}
