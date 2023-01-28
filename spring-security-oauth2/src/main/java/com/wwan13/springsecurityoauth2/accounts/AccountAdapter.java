package com.wwan13.springsecurityoauth2.accounts;

import org.springframework.security.core.userdetails.User;

public class AccountAdapter extends User {

    private Account account;

    public AccountAdapter(Account account) {
        super(account.getEmail(), account.getPassword(), account.getAuthorities());
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}
