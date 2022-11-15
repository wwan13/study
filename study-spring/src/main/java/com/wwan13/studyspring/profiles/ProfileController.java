package com.wwan13.studyspring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @PostMapping(value = "api/profiles", produces = "application/json")
    public ResponseEntity createProfile(@RequestBody Profile profile) {

        Profile newProfile = this.profileRepository.save(profile);
        URI createdUri = linkTo(methodOn(ProfileController.class).createProfile(profile)).slash(newProfile.getId()).toUri();

        return ResponseEntity.created(createdUri).body(profile);
    }

    @GetMapping(value = "api/profiles", produces = "application/json")
    public ResponseEntity getAllProfiles() {
        List allProfiles = profileRepository.findAll();

        return ResponseEntity.ok().body(allProfiles);
    }

    @GetMapping(value = "api/profiles/{id}", produces = "application/json")
    public ResponseEntity getProfileById(@PathVariable Integer id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return ResponseEntity.ok().body(profile);
    }

    @DeleteMapping(value = "api/profiles/{id}", produces = "application/json")
    public ResponseEntity deleteProfile(@PathVariable Integer id) {
        profileRepository.deleteById(id);
        return ResponseEntity.ok().body(id);
    }

}
