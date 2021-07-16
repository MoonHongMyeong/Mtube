package me.moon.Mtube.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.moon.Mtube.dto.comment.CommentSaveRequestDto;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private CommentMapper commentMapper;

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

    @Test
    @DisplayName("대댓글 등록에 성공한다.")
    public void addReplies() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        List<PostResponseDto> postlist = postMapper.getPostList();

        Long postId = postlist.get(0).getId();
        Long commentId = commentMapper.getCommentList(postId).get(0).getId();

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/video/"+postId+"/comment/"+commentId;

        CommentSaveRequestDto saveRequestDto = CommentSaveRequestDto.builder()
                .userId(userDto.getId())
                .content("testReplies")
                .video_post_id(postId)
                .build();

        mvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveRequestDto)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("댓글 수정에 성공한다.")
    public void updateComment() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        List<PostResponseDto> postlist = postMapper.getPostList();

        Long postId = postlist.get(0).getId();

        session.setAttribute("USER", userDto);

        String url = "http://localhost:"+port+"/api/v1/video/"+postId+"/comment";

        mvc.perform(put(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("expectedComment")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제에 성공한다.")
    public void deleteComment() throws Exception{
        LoginUserDto userDto = userMapper.findUserByEmail("test@test.com");
        MockHttpSession session = new MockHttpSession();

        List<PostResponseDto> postlist = postMapper.getPostList();

        Long postId = postlist.get(0).getId();

        session.setAttribute("USER", userDto);

        Long commentId = commentMapper.getCommentList(postId).get(0).getId();

        String url = "http://localhost:"+port+"/api/v1/video/"+postId+"/comment/"+commentId;

        mvc.perform(delete(url).session(session)).andExpect(status().isOk());


    }
}
