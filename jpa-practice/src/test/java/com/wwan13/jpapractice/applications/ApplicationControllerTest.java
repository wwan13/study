package com.wwan13.jpapractice.applications;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ApplicationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        List<String> answers = new ArrayList<>();
        answers.add("first");
        answers.add("second");

        String name = "name";
        Integer studentNumber = 20191234;

        Application application = Application.builder()
                .name(name)
                .studentNumber(studentNumber)
                .answers(answers)
                .build();

        this.applicationRepository.save(application);

    }


    @Test
    public void createApplication() throws Exception {

        List<String> answers = new ArrayList<>();
        answers.add("first");
        answers.add("second");

        String name = "taewan";
        Integer studentNumber = 20194059;

        Application application = Application.builder()
                .name(name)
                .studentNumber(studentNumber)
                .answers(answers)
                .build();

        this.mockMvc.perform(post("/api/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("studentNumber").value(studentNumber));

    }

    @Test
    public void getApplication() throws Exception {

        this.mockMvc.perform(get("/api/application/20191234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("studentNumber").value(20191234));

    }

}