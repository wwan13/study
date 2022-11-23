package com.wwan13.studyspring.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "auth/login", produces = "application/json")
public class AccountController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountService accountService;

    @PostMapping
    public ResponseEntity login(@RequestBody AccountDto.Request request) {

        Account account = accountService.findAccountById(request.getId());

        if(!request.getPw().equals(account.getPassword())) {
            return ResponseEntity.badRequest().body("pw error");
        }

        Authentication authentication = new UserAuthentication(request.getId(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok().body(token);
    }

}
