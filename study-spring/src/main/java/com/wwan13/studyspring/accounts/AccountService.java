package com.wwan13.studyspring.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account findAccountById(String id) {
        return this.accountRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("ID Not Exist"));
    }

    public Account getMyInfo() {
        return this.accountRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("no information"));
    }

}
