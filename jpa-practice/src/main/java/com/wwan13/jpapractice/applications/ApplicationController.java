package com.wwan13.jpapractice.applications;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/application", produces = "application/json")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    @PostMapping
    public ResponseEntity createApplication(@RequestBody Application application) {

        Application newApplication = this.applicationRepository.save(application);
        URI createdUri = linkTo(methodOn(ApplicationController.class).createApplication(application)).slash(newApplication.getId()).toUri();

        return ResponseEntity.created(createdUri).body(newApplication);
    }

    @GetMapping(value = "/{studentNumber}")
    public ResponseEntity getApplication(@PathVariable Integer studentNumber) {

        Application application = this.applicationRepository.findApplicationByStudentNumber(studentNumber)
                .orElseThrow(() -> new NullPointerException());

        return ResponseEntity.ok().body(application);

    }

}
