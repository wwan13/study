package com.wwan13.springsecurityoauth2.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class PostDto {

    private String title;

    private String contents;

}
