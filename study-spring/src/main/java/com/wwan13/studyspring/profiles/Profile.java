package com.wwan13.studyspring.profiles;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Profile {

    @Id @GeneratedValue
    private int id;
    private String name;
    private int age;
    private String job;

}
