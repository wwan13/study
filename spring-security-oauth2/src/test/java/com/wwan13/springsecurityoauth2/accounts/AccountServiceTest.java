package com.wwan13.springsecurityoauth2.accounts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public void findByUsername() {

        String username = "wwan13@naver.com";
        String password = "taewan";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        this.accountService.signup(account);

        UserDetailsService userDetailsService = (UserDetailsService) this.accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(userDetails.getPassword()).isEqualTo(this.accountService.passwordFormatter(password));

    }

    @Test
    public void findByUsernameFail() {

        String email = "wwan13@naver.com";

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername(email);
        });

        assertThat(exception.getMessage()).contains(email);

    }

    @Test
    public void passwordFormatter() {

        String password = "qwer1234";
        String formattedPassword = this.accountService.passwordFormatter(password);

        assertThat(formattedPassword).isEqualTo("{noop}"+password);

    }

    @Test
    public void signup() {

        String email = "wwan13@naver.com";
        String password = "qwer1234";

        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.signup(account);

        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getPassword()).isEqualTo(this.accountService.passwordFormatter(password));

    }

}