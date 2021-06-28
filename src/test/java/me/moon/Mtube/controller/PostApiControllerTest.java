package me.moon.Mtube.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.moon.Mtube.dto.playlist.ChannelPlaylistResponseDto;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.post.PostSaveRequestDto;
import me.moon.Mtube.dto.post.PostUpdateRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.PostMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private PostMapper postMapper;

    @Test
    @DisplayName("포스트 등록에 성공한다.")
    public void addPost() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();
        Long channelId=channelMapper.getChannelIdByChannelName("testChannelName");
        List<ChannelPlaylistResponseDto> channelsPlaylist = channelMapper.getChannelPlaylist(channelId);

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/video";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .channelId(channelId)
                .content("testVideo")
                .category("일반")
                .permitComment("Y")
                .temp("N")
                .title("testVideo")
                .videoFile("testVideo")
                .playlist(channelsPlaylist.get(0).getId())
                .build();

        mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("임시 포스트 등록에 성공한다.")
    public void addTempPost() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();
        Long channelId=channelMapper.getChannelIdByChannelName("testChannelName");
        List<ChannelPlaylistResponseDto> channelsPlaylist = channelMapper.getChannelPlaylist(channelId);

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/video/temp";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .channelId(channelId)
                .content("testVideo")
                .category("일반")
                .permitComment("Y")
                .temp("N")
                .title("testVideo")
                .videoFile("testVideo")
                .playlist(channelsPlaylist.get(0).getId())
                .build();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }
    
    @Test
    @DisplayName("포스트 수정에 성공한다.")
    public void updatePost() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();
        Long channelId=channelMapper.getChannelIdByChannelName("testChannelName");
        List<ChannelPlaylistResponseDto> channelsPlaylist = channelMapper.getChannelPlaylist(channelId);
        List<PostResponseDto> postList = postMapper.getChannelPostList(channelId);
        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/video/"+postList.get(0).getId();

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title("exceptedTitle")
                .build();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("포스트 삭제에 성공한다")
    public void deletePost() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();
        Long channelId=channelMapper.getChannelIdByChannelName("testChannelName");
        List<ChannelPlaylistResponseDto> channelsPlaylist = channelMapper.getChannelPlaylist(channelId);
        List<PostResponseDto> postList = postMapper.getChannelPostList(channelId);

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/video/"+postList.get(0).getId();

        mvc.perform(delete(url).session(session))
                .andExpect(status().isOk());
    }
}
