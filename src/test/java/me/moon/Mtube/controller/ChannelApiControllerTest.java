package me.moon.Mtube.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.channel.ChannelUpdateRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistResponseDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistSaveRequestDto;
import me.moon.Mtube.dto.playlist.ChannelPlaylistUpdateRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.dto.user.UserSaveRequestDto;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChannelApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

//    @BeforeAll
//    public void setup() throws Exception {
//        String url = "http://localhost:"+port+"/api/v1/user";
//        UserSaveRequestDto saveRequestDto = UserSaveRequestDto.builder()
//                .email("test@test.com")
//                .name("testUser")
//                .password("12345")
//                .picture("/test.png")
//                .build();
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(saveRequestDto)))
//                .andExpect(status().isCreated());
//    }

//    @AfterAll
//    public void clean() throws Exception {
//        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
//        String url = "http://localhost:"+port+"/api/v1/user/"+userDto.getId();
//
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("USER", new UserResponseDto(userDto));
//
//        mvc.perform(delete(url).session(session))
//                .andExpect(status().isOk());
//
//
//    }

    @Test
    @DisplayName("?????? ????????? ????????????")
    public void addChannel() throws Exception {
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("USER", new UserResponseDto(userDto));

        String url = "http://localhost:"+port+"/api/v1/channel";
        ChannelSaveRequestDto saveRequestDto = ChannelSaveRequestDto.builder()
                .name("testChannelName")
                .description("testChannelName")
                .build();
        saveRequestDto.setUser(userDto.getId());

        mvc.perform(post(url)
                .session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(saveRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    public void updateChannelDescription() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("USER", new UserResponseDto(userDto));

        Long channelId=channelMapper.getChannelIdByChannelName("testChannelName");

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId;

        ChannelUpdateRequestDto updateRequestDto = ChannelUpdateRequestDto.builder()
                .channelId(channelId)
                .description("expectedDescription")
                .build();

        mvc.perform(put(url).
                session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk());

        assertThat(channelMapper
                .getChannel(channelId).getDescription()
                .equals("expectedDescription"));
    }

    @Test
    @DisplayName("?????? ????????? ????????????.")
    public void deleteChannel() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("USER", new UserResponseDto(userDto));

        Long channelId=channelMapper.getChannelIdByChannelName("testChannelName");

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId;

        mvc.perform(delete(url).session(session)).andExpect(status().isOk());
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy((ThrowableAssert.ThrowingCallable) channelMapper.getChannel(channelId));
    }

    @Test
    @DisplayName("????????? ?????????????????? ??????")
    public void addChannelPlaylist() throws Exception{
        Long channelId = channelMapper.getChannelIdByChannelName("testChannelName");

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/playlist";

        ChannelPlaylistSaveRequestDto saveRequestDto = ChannelPlaylistSaveRequestDto.builder()
                .channelId(channelId)
                .name("testChannelPlaylist")
                .build();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("????????? ?????????????????? ?????? ??????")
    public void updateChannelPlaylist() throws Exception{
        Long channelId = channelMapper.getChannelIdByChannelName("testChannelName");
        List<ChannelPlaylistResponseDto> playlist = channelMapper.getChannelPlaylist(channelId);

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/playlist/"+playlist.get(1).getId();

        ChannelPlaylistUpdateRequestDto requestDto = ChannelPlaylistUpdateRequestDto.builder()
                .id(playlist.get(1).getId())
                .name("ExpectedPlaylistName")
                .build();

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());

    }

    @Test
    @DisplayName("????????? ?????????????????? ??????")
    public void deleteChannelPlaylist() throws Exception{
        Long channelId = channelMapper.getChannelIdByChannelName("testChannelName");
        List<ChannelPlaylistResponseDto> playlist = channelMapper.getChannelPlaylist(channelId);

        String url = "http://localhost:"+port+"/api/v1/channel/"+channelId+"/playlist/"+playlist.get(1).getId();


        mvc.perform(delete(url)).andExpect(status().isOk());

    }
}
