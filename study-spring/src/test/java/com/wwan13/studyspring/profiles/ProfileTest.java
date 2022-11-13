package com.wwan13.studyspring.profiles;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    @Test
    public void createProfile() {
        Profile profile = Profile.builder()
                .name("kim")
                .age(22)
                .job("student")
                .build();

        assertThat(profile.getName()).isEqualTo("kim");
        assertThat(profile.getAge()).isEqualTo(22);
        assertThat(profile.getJob()).isEqualTo("student");
    }
}