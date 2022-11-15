package com.wwan13.studyspring.profiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    public void before() throws Exception {

        Profile profile1 = Profile.builder()
                .name("kim")
                .age(23)
                .job("student")
                .build();
        this.mockMvc.perform(post("/api/profiles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(profile1)));

        Profile profile2 = Profile.builder()
                .name("lee")
                .age(22)
                .job("student")
                .build();
        this.mockMvc.perform(post("/api/profiles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(profile2)));

    }

    @AfterEach
    public void after() {

        profileRepository.deleteAll();

    }

    /**
     * POST api/profiles
     */
    @Test
    public void createProfile() throws Exception {
        Profile profile = Profile.builder()
                .name("kim")
                .age(23)
                .job("student")
                .build();

        this.mockMvc.perform(post("/api/profiles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(profile)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("kim"))
                .andExpect(jsonPath("age").value(23))
                .andExpect(jsonPath("job").value("student"));
    }

    /**
     * GET api/profiles
     */
    @Test
    public void getAllProfiles() throws Exception {

        this.mockMvc.perform(get("/api/profiles/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * GET api/profiles/{id}
     */
    @Test
    public void getProfileById() throws Exception {

        this.mockMvc.perform(get("/api/profiles/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * DELETE api/profile/{id}
     */
    @Test public void deleteProfile() throws Exception {

        this.mockMvc.perform(delete("/api/profiles/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}