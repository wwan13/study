package com.wwan13.springsecurityoauth2.configs;

import com.wwan13.springsecurityoauth2.accounts.Account;
import com.wwan13.springsecurityoauth2.accounts.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AppConfigTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void applicationRunner() {

        String email = "admin@email.com";
        Account adminUser = accountRepository.findByEmail(email).get();

        assertThat(adminUser.getEmail()).isEqualTo(email);

    }

}