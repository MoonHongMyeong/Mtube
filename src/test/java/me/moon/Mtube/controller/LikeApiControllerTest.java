package me.moon.Mtube.controller;

import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.mapper.CommentMapper;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikeApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    @DisplayName("포스트의 좋아요에 성공한다.")
    public void likePost() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        List<PostResponseDto> postlist = postMapper.getPostList();

        Long postId = postlist.get(0).getId();

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/video/"+postId+"/like";

        mvc.perform(post(url).session(session)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("포스트 싫어요에 성공한다")
    public void dislikePost() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        List<PostResponseDto> postlist = postMapper.getPostList();

        Long postId = postlist.get(0).getId();

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/video/"+postId+"/dislike";

        mvc.perform(post(url).session(session)).andExpect(status().isOk());

    }
}
