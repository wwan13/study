package com.wwan13.studyspring.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = this.getJwtFromRequest(request);
            if(!jwtToken.isBlank() && JwtTokenProvider.validationToken(jwtToken)) {
                String userId = JwtTokenProvider.getUserIdFromJwt(jwtToken);

                UserAuthentication authentication = new UserAuthentication(userId, null, null); // id 인증
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 기본적으로 제공한 details 세팅

                SecurityContextHolder.getContext().setAuthentication(authentication); // 세션서 계속 사용하기 위해 등록
            } else {
                if(jwtToken.isBlank()) {
                    request.setAttribute("unAuthorization", "401 인증키 없음.");
                }
                if(!JwtTokenProvider.validationToken(jwtToken)) {
                    request.setAttribute("unAuthorization", "401-001 인증키 만료.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!bearerToken.isBlank() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }
}
