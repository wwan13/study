package com.wwan13.springsecurityoauth2.accounts;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)     // role 이 여러개랑 매핑될 수 있기 떄문에 사용
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
}
