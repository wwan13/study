package com.wwan13.studyspring.accounts;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findUserByUsername() {

        Account account = Account.builder()
                .email("taewan@email.com")
                .password("wwan")
                .roles(AccountRoles.USER)
                .build();
        this.accountRepository.save(account);

        UserDetailsService userDetailsService = (UserDetailsService)accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername("taewan@email.com");

        assertThat(userDetails.getPassword()).isEqualTo("wwan");
    }

}