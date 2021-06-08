package me.moon.Mtube.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.moon.Mtube.dto.channel.ChannelSaveRequestDto;
import me.moon.Mtube.dto.user.LoginUserDto;
import me.moon.Mtube.dto.user.UserResponseDto;
import me.moon.Mtube.dto.user.UserSaveRequestDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeAll
    public void setup() throws Exception {
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
    @DisplayName("채널 등록에 성공한다")
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
}
