package com.wwan13.studyspring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "api/profiles", produces = "application/json")
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @PostMapping
    public ResponseEntity createProfile(@RequestBody Profile profile) {

        Profile newProfile = this.profileRepository.save(profile);
        URI createdUri = linkTo(ProfileController.class).slash(newProfile.getId()).toUri();

        return ResponseEntity.created(createdUri).body(profile);
    }
}
