package com.wwan13.studyspringsecurity.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwan13.studyspringsecurity.User.Authorities;
import com.wwan13.studyspringsecurity.User.User;
import com.wwan13.studyspringsecurity.User.UserRepository;
import com.wwan13.studyspringsecurity.User.UserRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() throws Exception {

        User user1 = User.builder()
                .username("ktw01")
                .password("1q2w3e4r")
                .authority(Authorities.ROLE_USER)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .username("ktw02")
                .password("1q2w3e4r")
                .authority(Authorities.ROLE_USER)
                .build();
        userRepository.save(user2);

    }

    @Test
    @DisplayName("POST /auth/signup - success")
    public void signupTest() throws Exception {

        String username = "wwan13";
        String password = "qwer1234";

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername(username);
        userRequestDto.setPassword(password);

        this.mockMvc.perform(post("/auth/signup")
                        .content(this.objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(username));

    }

    @Test
    @DisplayName("POST /auth/signup - already exist")
    public void signupTest_alreadyExist() throws Exception {

        String username = "ktw01";
        String password = "1q2w3e4r";

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername(username);
        userRequestDto.setPassword(password);

        this.mockMvc.perform(post("/auth/signup")
                        .content(this.objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(500));
//                .andExpect(
//                        result -> assertTrue(result.getResolvedException() instanceof RuntimeException)
//                );

    }

    @Test
    @DisplayName("POST /auth/login - success")
    public void loginTest() throws Exception{

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("ktw01");
        userRequestDto.setPassword("1q2w3e4r");

        this.mockMvc.perform(post("/auth/login")
                        .content(this.objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getUserTest() throws Exception{

        User user = userRepository.findByUsername("ktw01").get();
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo("ktw01");
        System.out.println(user.getUsername() + " - " + user.getPassword());

    }

}