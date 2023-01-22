package com.wwan13.springsecurityoauth2.configs;

import com.wwan13.springsecurityoauth2.accounts.Account;
import com.wwan13.springsecurityoauth2.accounts.AccountRepository;
import com.wwan13.springsecurityoauth2.accounts.AccountRole;
import com.wwan13.springsecurityoauth2.accounts.AccountService;
import com.wwan13.springsecurityoauth2.commons.AppProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public ApplicationRunner applicationRunner() {

        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;
            @Autowired
            AccountRepository accountRepository;
            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                this.accountRepository.deleteAll();

                Account admin = Account.builder()
                        .email("admin@email.com")
                        .password("admin")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();
                accountService.signup(admin);

                Account user1 = Account.builder()
                        .email("audwls2lee@naver.com")
                        .password("qwer1234")
                        .roles(Set.of(AccountRole.USER))
                        .build();
                accountService.signup(user1);
            }
        };

    }

}
