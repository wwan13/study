package com.wwan13.springsecurityoauth2.posts;

import com.wwan13.springsecurityoauth2.accounts.Account;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Builder @Getter @Setter
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String contents;

    private LocalDateTime createdAt;

    @ManyToOne
    private Account user;
}
