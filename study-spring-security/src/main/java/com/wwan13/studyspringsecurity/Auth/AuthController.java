package com.wwan13.studyspringsecurity.Auth;

import com.wwan13.studyspringsecurity.User.UserRequestDto;
import com.wwan13.studyspringsecurity.User.UserResponseDto;
import com.wwan13.studyspringsecurity.jwt.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody UserRequestDto userRequestDto) {

        UserResponseDto userResponseDto = authService.signup(userRequestDto);
        return ResponseEntity.ok().body(userRequestDto);

    }

    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody UserRequestDto userRequestDto) {

        TokenDto tokenDto = authService.login(userRequestDto);
        return ResponseEntity.ok().body(tokenDto);

    }
}
