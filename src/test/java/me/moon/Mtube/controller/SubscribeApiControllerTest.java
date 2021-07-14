package me.moon.Mtube.controller;

import me.moon.Mtube.dto.channel.ChannelResponseDto;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.SubscribeMapper;
import me.moon.Mtube.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubscribeApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    @Test
    @DisplayName("채널 구독에 성공한다.")
    public void subscribe() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        Long channelId = channelMapper.getChannelIdByChannelName("testChannel");

        session.setAttribute("USER", userDto);

        String url = "localhost:"+port+"/api/v1/channel/"+channelId+"/subscribe";

        mvc.perform(post(url).session(session)).andExpect(status().isCreated());
    }
}
