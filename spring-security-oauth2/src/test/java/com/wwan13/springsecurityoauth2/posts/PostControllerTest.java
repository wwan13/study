package com.wwan13.springsecurityoauth2.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwan13.springsecurityoauth2.accounts.Account;
import com.wwan13.springsecurityoauth2.accounts.AccountRole;
import com.wwan13.springsecurityoauth2.accounts.AccountService;
import com.wwan13.springsecurityoauth2.commons.AppProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    AppProperties appProperties;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("성공적으로 post 를 생성하는 테스트")
    public void createPost() throws Exception {

        PostDto postDto = PostDto.builder()
                .title("title")
                .contents("contents")
                .build();

        this.mockMvc.perform(post("/api/posts")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("인증토큰 없이 post 를 생성하는 테스트")
    public void createPost_WithoutAccessToken() throws Exception {

        PostDto postDto = PostDto.builder()
                .title("title")
                .contents("contents")
                .build();

        this.mockMvc.perform(post("/api/posts")
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private String getBearerToken(boolean needToCreateAccount) throws Exception {
        return "Bearer " + getAccessToken(needToCreateAccount);
    }

    private String getAccessToken(boolean needToCreateAccount) throws Exception {

        if (needToCreateAccount) {
            createAccount();
        }

        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(this.appProperties.getClientId(), this.appProperties.getClientSecret()))
                .param("username", this.appProperties.getTestUser1Username())
                .param("password", this.appProperties.getTestUser1Password())
                .param("grant_type", "password"));

        String responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();

    }

    public Account createAccount() {

        Account newAccount = Account.builder()
                .email(this.appProperties.getTestUser1Username())
                .password(this.appProperties.getTestUser1Password())
                .roles(Set.of(AccountRole.USER))
                .build();

        return this.accountService.signup(newAccount);
    }

}