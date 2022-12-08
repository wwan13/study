package com.wwan13.studyspringsecurity.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 실제 필터링 로직이 실행되는 곳
        // 가입/로그인/재발급을 제외한 모든 Request 는 이 함수를 거침
        // 요청이 컨트롤러에 정상적으로 도착했다면 SecurityContext 에 UserID가 존재하는것이 보장됨
        // 직접 DB를 조회한것이 아니기 때문에 탈퇴로 인해 MemberID 가 없는 경우엔 Service 단에서 고려해야함

        String jwtToken = resolveToken(request);                                            // request 에서 토큰 가져오기

        if (StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication); // 토큰 유효성 검사 이후 해당 토큰을 SecurityContext 에 저장
        }

        filterChain.doFilter(request, response);
    }

    // Request header 에서 토큰 정보 가져오기
    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }
}
