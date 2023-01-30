package com.wwan13.jpapractice.applications;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Application {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Long studentNumber;

    @ElementCollection
    private List<Answer> answers = new ArrayList<>();

    private LocalDateTime update_at;

}
