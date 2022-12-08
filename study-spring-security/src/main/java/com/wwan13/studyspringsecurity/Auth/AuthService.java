package com.wwan13.studyspringsecurity.Auth;

import com.wwan13.studyspringsecurity.User.User;
import com.wwan13.studyspringsecurity.User.UserRepository;
import com.wwan13.studyspringsecurity.User.UserRequestDto;
import com.wwan13.studyspringsecurity.User.UserResponseDto;
import com.wwan13.studyspringsecurity.jwt.RefreshToken;
import com.wwan13.studyspringsecurity.jwt.RefreshTokenRepository;
import com.wwan13.studyspringsecurity.jwt.TokenDto;
import com.wwan13.studyspringsecurity.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    public TokenDto login(UserRequestDto userRequestDto) {

        // request 의 id, pw를 바탕으로 authentication token 생성
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();

        System.out.println("service 2");
        System.out.println(authenticationToken.toString());
        // 검증 (비밀번호 체크) 이 이뤄지는 부분
        // authenticate 메서드가 실행될때 CustomUserDetailService 의 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보로 토큰 생성
        TokenDto tokenDto = tokenProvider.generateToken(authentication);

        // refresh token
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;

    }



}
