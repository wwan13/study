package com.wwan13.jpapractice.applications;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Optional<Application> findApplicationByStudentNumber(Integer studentNumber);

}
