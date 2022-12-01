package com.wwan13.studyspringsecurity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String username;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
