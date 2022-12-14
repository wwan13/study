package com.wwan13.studyspringsecurity.Auth;

import com.wwan13.studyspringsecurity.User.User;
import com.wwan13.studyspringsecurity.User.UserRepository;
import com.wwan13.studyspringsecurity.User.UserRequestDto;
import com.wwan13.studyspringsecurity.User.UserResponseDto;
import com.wwan13.studyspringsecurity.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody UserRequestDto userRequestDto) {

        UserResponseDto userResponseDto = authService.signup(userRequestDto);

        User savedUser = userRepository.findByUsername(userRequestDto.getUsername()).get();
        System.out.println(savedUser.getId());
        System.out.println(savedUser.getUsername());
        System.out.println(savedUser.getPassword());

        return ResponseEntity.ok().body(userResponseDto);

    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserRequestDto userRequestDto) {

        TokenDto tokenDto = authService.login(userRequestDto);
        return ResponseEntity.ok().body(tokenDto);

}
}
