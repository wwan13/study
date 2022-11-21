package com.wwan13.studyspring.accounts;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

// db에 USER 이라는 테이블 이름으로는 매핑 불가.
// @TABLE 애노테이션을 사용해 다른 이름으로 매핑 가능 하나 원천적으로 해결하기 위해 ACCOUNT 이름 사용

@Entity
@Builder @Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    private Integer Id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRoles roles;
}
