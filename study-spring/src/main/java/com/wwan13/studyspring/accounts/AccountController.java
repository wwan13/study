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
@RequestMapping(value = "auth/", produces = "application/json")
public class AccountController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountService accountService;

    @PostMapping(value = "login")
    public ResponseEntity logIn(@RequestBody AccountDto accountDto) {
        Account account = accountService.findAccountById(accountDto.getId());

        if(!accountDto.getPassword().equals(account.getPassword())) {
            return ResponseEntity.badRequest().body("pw error");
        }

        Authentication authentication = new UserAuthentication(accountDto.getId(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping(value = "signup")
    public ResponseEntity signUp(@RequestBody AccountDto accountDto) {
        Account account = this.accountService.createAccount(accountDto);
        Authentication authentication = new UserAuthentication(accountDto.getId(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok().body(token);

    }

}
