package com.wwan13.studyspring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity getAllProfiles() {
        List allProfiles = profileRepository.findAll();

        return ResponseEntity.ok().body(allProfiles);
    }
}