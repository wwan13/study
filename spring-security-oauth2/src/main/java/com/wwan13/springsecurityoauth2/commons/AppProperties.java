package com.wwan13.springsecurityoauth2.commons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "my-app")
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private String adminUsername;

    @NotEmpty
    private String adminPassword;

    @NotEmpty
    private String user1Username;

    @NotEmpty
    private String user1Password;

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String clientSecret;

}
