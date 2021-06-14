package me.moon.Mtube.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.moon.Mtube.dto.playlist.UserPlaylistResponseDto;
import me.moon.Mtube.dto.user.*;
import me.moon.Mtube.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class UserApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    @DisplayName("회원 가입에 성공한다.")
    public void signUp() throws Exception {
        String url = "http://localhost:"+port+"/api/v1/user";
        UserSaveRequestDto saveRequestDto = UserSaveRequestDto.builder()
                .email("test@test.com")
                .name("testUser")
                .password("12345")
                .picture("/test.png")
                .build();
        mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(saveRequestDto)))
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    @DisplayName("회원 정보 수정에 성공한다.")
    public void updateUser() throws Exception {
        String exceptName = "updateUser";
        String exceptPicture = "/update.png";

        LoginUserDto loginUserDto = userMapper.findUserByEmail("test@test.com");

        String url = "http://localhost:"+port+"/api/v1/user/"+loginUserDto.getId();

        UserUpdateRequestDto updateRequestDto = UserUpdateRequestDto.builder()
                .name(exceptName)
                .picture(exceptPicture)
                .build();
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk());

        UserResponseDto userResponseDto = userMapper.getUser(loginUserDto.getId());
        assertThat(userResponseDto.getName().equals(exceptName));
        assertThat(userResponseDto.getPicture().equals(exceptPicture));

    }

    @Test
    @Order(3)
    @DisplayName("비밀번호 변경에 성공한다.")
    public void changePassword() throws Exception {
        LoginUserDto loginUserDto = userMapper.findUserByEmail("test@test.com");

        MockHttpSession session = new MockHttpSession();

        String url = "http://localhost:"+port+"/api/v1/user/"+loginUserDto.getId()+"/password";

        session.setAttribute("USER", userMapper.getUser(loginUserDto.getId()));

        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder()
                .password("12345")
                .newPassword("54321")
                .build();

        mvc.perform(put(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userChangePasswordDto)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("회원가입시 기본으로 '나중에 볼 영상' 리스트가 생성된다.")
    public void basicUserPlaylist(){
        LoginUserDto loginUserDto = userMapper.findUserByEmail("test@test.com");
        List<UserPlaylistResponseDto> usersPlaylist=userMapper.getUserPlaylistList(loginUserDto.getId());

        assertThat(usersPlaylist.get(0).getName().equals("나중에 볼 동영상"));
    }

    @Test
    @Order(5)
    @DisplayName("유저의 플레이리스트를 생성한다.")
    public void addUserPlaylist() throws Exception {
        LoginUserDto loginUserDto = userMapper.findUserByEmail("test@test.com");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("USER", new UserResponseDto(loginUserDto));
        String url = "http://localhost:"+port+"/api/v1/user/"+loginUserDto.getId()+"/playlist";

        mvc.perform(post(url)
                .session(session)
                .param("name", "testPlaylist"))
                .andExpect(status().isCreated());
    }
    @Test
    @Order(6)
    @DisplayName("회원탈퇴에 성공한다.")
    public void withdrawal() throws Exception {
        LoginUserDto loginUserDto = userMapper.findUserByEmail("test@test.com");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("USER", new UserResponseDto(loginUserDto));
        String url = "http://localhost:"+port+"/api/v1/user/"+loginUserDto.getId();

        mvc.perform(delete(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
