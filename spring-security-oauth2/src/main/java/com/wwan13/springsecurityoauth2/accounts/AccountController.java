package com.wwan13.springsecurityoauth2.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/signup")
    public void signup(@RequestBody Account account) {
        Account newAccount = this.accountService.signup(account);

    }
}
