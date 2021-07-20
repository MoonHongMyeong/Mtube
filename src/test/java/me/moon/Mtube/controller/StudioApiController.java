package me.moon.Mtube.controller;

import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.mapper.ChannelMapper;
import me.moon.Mtube.mapper.CommentMapper;
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

import java.nio.channels.Channel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudioApiController {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    @DisplayName("채널의 포스트에 달린 댓글에 하트 주기")
    public void giveHeart() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        Long channelId = channelMapper.getChannelIdByChannelName("testChannel");
        Long commentId = commentMapper.videoOwnerGetCommentList(channelId).get(0).getId();
        session.setAttribute("USER", userDto);

        String url = "localhost:"+port+"/api/v1/studio/"+channelId+"/comment/"+commentId+"/heart";

        mvc.perform(put(url).session(session)).andExpect(status().isOk());
    }
}
