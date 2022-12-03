package com.wwan13.studyspringsecurity.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwan13.studyspringsecurity.User.UserRequestDto;
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

}