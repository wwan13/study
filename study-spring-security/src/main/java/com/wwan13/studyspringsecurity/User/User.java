package com.wwan13.studyspringsecurity.User;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "USERS")
public class User {

    @Id @GeneratedValue
    private long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Authorities authority;

}
