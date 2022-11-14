package com.wwan13.studyspring.profiles;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    /**
     * Profile 엔티티가 잘 생성되는지 확인하는 테스트
     */
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