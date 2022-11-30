package com.wwan13.studyspringsecurity.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http){
        // Security 로직에 customFilter 를 적용

        JwtFilter customFilter = new JwtFilter();
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
