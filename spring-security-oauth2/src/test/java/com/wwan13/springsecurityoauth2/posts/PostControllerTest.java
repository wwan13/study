package com.wwan13.springsecurityoauth2.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwan13.springsecurityoauth2.accounts.Account;
import com.wwan13.springsecurityoauth2.accounts.AccountAdapter;
import com.wwan13.springsecurityoauth2.accounts.AccountRole;
import com.wwan13.springsecurityoauth2.accounts.AccountService;
import com.wwan13.springsecurityoauth2.commons.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    PostService postService;
    @Autowired
    AppProperties appProperties;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {

        AccountAdapter accountAdapter = (AccountAdapter) this.accountService.loadUserByUsername(this.appProperties.getUser1Username());

        this.postService.deleteAllPosts();

        PostDto postDto1 = PostDto.builder()
                .title("title1")
                .contents("contents1")
                .build();
        this.postService.createPost(postDto1, accountAdapter.getAccount());

        PostDto postDto2 = PostDto.builder()
                .title("title1")
                .contents("contents1")
                .build();
        this.postService.createPost(postDto2, accountAdapter.getAccount());

    }

    @Test
    @DisplayName("성공적으로 post 를 생성하는 테스트")
    public void createPost() throws Exception {

        String title = "this is title";
        String contents = "this is contents";

        PostDto postDto = PostDto.builder()
                .title(title)
                .contents(contents)
                .build();

        this.mockMvc.perform(post("/api/posts")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(title))
                .andExpect(jsonPath("contents").value(contents))
                .andExpect(jsonPath("manager").exists());

    }

    @Test
    @DisplayName("인증토큰 없이 post 를 생성하는 테스트")
    public void createPost_WithoutAccessToken() throws Exception {

        String title = "this is title";
        String contents = "this is contents";

        PostDto postDto = PostDto.builder()
                .title(title)
                .contents(contents)
                .build();

        this.mockMvc.perform(post("/api/posts")
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllPosts() throws Exception {

        this.mockMvc.perform(get("/api/posts"))
                .andDo(print())
                .andExpect(status().isOk());

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