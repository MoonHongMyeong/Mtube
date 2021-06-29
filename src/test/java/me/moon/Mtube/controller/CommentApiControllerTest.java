package me.moon.Mtube.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;
import me.moon.Mtube.dto.post.PostResponseDto;
import me.moon.Mtube.dto.user.LoginUserDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Test
    @DisplayName("댓글 등록에 성공한다.")
    public void addComment() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        List<PostResponseDto> postlist = postMapper.getPostList();

        Long postId = postlist.get(0).getId();

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/video/"+postId+"/comment";

        CommentSaveRequestDto saveRequestDto = CommentSaveRequestDto.builder()
                .userId(userDto.getId())
                .content("testComment")
                .video_post_id(postId)
                .build();

        mvc.perform(post(url)
        .session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(saveRequestDto))).andExpect(status().isCreated());

    }

}
