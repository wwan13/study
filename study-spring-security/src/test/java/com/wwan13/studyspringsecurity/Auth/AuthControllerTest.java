package com.wwan13.studyspringsecurity.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwan13.studyspringsecurity.User.Authorities;
import com.wwan13.studyspringsecurity.User.User;
import com.wwan13.studyspringsecurity.User.UserRepository;
import com.wwan13.studyspringsecurity.User.UserRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
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
    public void signupTest() throws Exception {

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("wwan13");
        userRequestDto.setPassword("qwer1234");

        this.mockMvc.perform(post("/auth/signup")
                        .content(this.objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void loginTest() throws Exception{

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("ktw01");
        userRequestDto.setPassword("1q2w3e4r");

        //login
        this.mockMvc.perform(post("/auth/login")
                        .content(this.objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @Test
    public void getUserTest() throws Exception{

        User user = userRepository.findByUsername("ktw01").get();
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo("ktw01");

    }

}