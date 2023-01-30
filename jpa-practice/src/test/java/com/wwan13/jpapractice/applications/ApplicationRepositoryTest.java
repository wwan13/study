package com.wwan13.jpapractice.applications;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ApplicationRepositoryTest {

    @Autowired
    ApplicationRepository applicationRepository;

    @Test
    public void findApplicationByStudentNumber() {

        List<String> answers = new ArrayList<>();
        answers.add("first");
        answers.add("second");

        String name = "name";
        Integer studentNumber = 20191234;

        Application application = Application.builder()
                .name(name)
                .studentNumber(studentNumber)
                .answers(answers)
                .build();

        this.applicationRepository.save(application);

        Application application1 = this.applicationRepository.findApplicationByStudentNumber(studentNumber).get();

        assertThat(application1.getName()).isEqualTo(application.getName());

    }

}